package com.github.isaconstance.ms.alerta.service;

import com.github.isaconstance.ms.alerta.client.IdosoClient;
import com.github.isaconstance.ms.alerta.client.dto.IdosoResponseDTO;
import com.github.isaconstance.ms.alerta.client.dto.StatusIdoso;
import com.github.isaconstance.ms.alerta.dto.AlertaRequestDTO;
import com.github.isaconstance.ms.alerta.dto.AlertaResponseDTO;
import com.github.isaconstance.ms.alerta.entities.*;
import com.github.isaconstance.ms.alerta.exceptions.DatabaseException;
import com.github.isaconstance.ms.alerta.exceptions.ResourceNotFoundException;
import com.github.isaconstance.ms.alerta.repository.AlertaRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final IdosoClient idosoClient;

    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> getAll() {
        return alertaRepository.findAll()
                .stream()
                .map(AlertaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlertaResponseDTO getById(Long id) {
        return new AlertaResponseDTO(findAlertaById(id));
    }

    @Transactional
    public AlertaResponseDTO create(AlertaRequestDTO dto) {

        IdosoResponseDTO idoso = findIdosoById(dto.getIdosoId());

        Alerta alerta = new Alerta();
        alerta.setIdosoId(dto.getIdosoId());
        alerta.setTipo(dto.getTipo());
        alerta.setGravidade(dto.getGravidade());
        alerta.setOrigem(dto.getOrigem());
        alerta.setMensagem(dto.getMensagem());
        alerta.setDataCriacao(LocalDateTime.now());
        alerta.setStatus(StatusAlerta.ABERTO);
        alerta.setStatusSincronizado(true);

        alerta = alertaRepository.save(alerta);

        synchronizeStatus(alerta, idoso.getStatus());

        return new AlertaResponseDTO(alertaRepository.save(alerta));
    }

    @Transactional
    public AlertaResponseDTO update(Long id, AlertaRequestDTO dto) {

        Alerta alerta = findAlertaById(id);

        if (alerta.getStatus() == StatusAlerta.RESOLVIDO) {
            throw new DatabaseException(
                    "Não é possível alterar um alerta resolvido."
            );
        }

        if (!alerta.getIdosoId().equals(dto.getIdosoId())) {
            throw new DatabaseException(
                    "Não é permitido alterar o idoso vinculado ao alerta."
            );
        }

        IdosoResponseDTO idoso = findIdosoById(alerta.getIdosoId());

        alerta.setTipo(dto.getTipo());
        alerta.setGravidade(dto.getGravidade());
        alerta.setOrigem(dto.getOrigem());
        alerta.setMensagem(dto.getMensagem());

        alerta = alertaRepository.save(alerta);

        List<Alerta> alertasAtivos =
                alertaRepository.findByIdosoIdAndStatusNot(
                        alerta.getIdosoId(),
                        StatusAlerta.RESOLVIDO
                );

        StatusIdoso novoStatus = alertasAtivos.stream()
                .map(this::convertGravidadeToStatus)
                .max(Comparator.comparingInt(this::statusLevel))
                .orElse(StatusIdoso.ESTAVEL);

        updateIdosoStatus(alerta, idoso.getStatus(), novoStatus, true);

        return new AlertaResponseDTO(alertaRepository.save(alerta));
    }

    @Transactional
    public void delete(Long id) {
        Alerta alerta = findAlertaById(id);
        alertaRepository.delete(alerta);
    }

    @Transactional
    public AlertaResponseDTO acompanhar(Long id) {

        Alerta alerta = findAlertaById(id);

        if (alerta.getStatus() != StatusAlerta.ABERTO) {
            throw new DatabaseException(
                    "Somente alertas abertos podem entrar em acompanhamento."
            );
        }

        alerta.setStatus(StatusAlerta.EM_ACOMPANHAMENTO);

        return new AlertaResponseDTO(alertaRepository.save(alerta));
    }

    @Transactional
    public AlertaResponseDTO resolver(Long id) {

        Alerta alerta = findAlertaById(id);

        if (alerta.getStatus() == StatusAlerta.RESOLVIDO) {
            throw new DatabaseException("Este alerta já foi resolvido.");
        }

        alerta.setStatus(StatusAlerta.RESOLVIDO);
        alertaRepository.save(alerta);

        IdosoResponseDTO idoso = findIdosoById(alerta.getIdosoId());

        List<Alerta> alertasAtivos =
                alertaRepository.findByIdosoIdAndStatusNot(
                        alerta.getIdosoId(),
                        StatusAlerta.RESOLVIDO
                );

        StatusIdoso novoStatus = alertasAtivos.stream()
                .map(this::convertGravidadeToStatus)
                .max(Comparator.comparingInt(this::statusLevel))
                .orElse(StatusIdoso.ESTAVEL);

        updateIdosoStatus(alerta, idoso.getStatus(), novoStatus, true);

        return new AlertaResponseDTO(alertaRepository.save(alerta));
    }

    private Alerta findAlertaById(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alerta não encontrado. ID: " + id
                        )
                );
    }

    private IdosoResponseDTO findIdosoById(Long id) {
        try {
            return idosoClient.getIdosoById(id);

        } catch (FeignException.NotFound exception) {
            throw new ResourceNotFoundException(
                    "Idoso não encontrado. ID: " + id
            );
        }
    }

    private void synchronizeStatus(
            Alerta alerta,
            StatusIdoso statusAtual) {

        StatusIdoso novoStatus = convertGravidadeToStatus(alerta);

        updateIdosoStatus(alerta, statusAtual, novoStatus, false);
    }

    private void updateIdosoStatus(
            Alerta alerta,
            StatusIdoso statusAtual,
            StatusIdoso novoStatus,
            boolean allowDowngrade) {

        boolean shouldUpdate = allowDowngrade
                ? novoStatus != statusAtual
                : statusLevel(novoStatus) > statusLevel(statusAtual);

        if (!shouldUpdate) {
            alerta.setStatusSincronizado(true);
            return;
        }

        try {
            idosoClient.updateStatus(
                    alerta.getIdosoId(),
                    novoStatus
            );

            alerta.setStatusSincronizado(true);

        } catch (Exception exception) {
            alerta.setStatusSincronizado(false);
        }
    }

    private StatusIdoso convertGravidadeToStatus(Alerta alerta) {

        return switch (alerta.getGravidade()) {
            case BAIXA, MEDIA -> StatusIdoso.ATENCAO;
            case ALTA -> StatusIdoso.ALERTA;
            case CRITICA -> StatusIdoso.EMERGENCIA;
        };
    }

    private int statusLevel(StatusIdoso status) {

        return switch (status) {
            case ESTAVEL -> 0;
            case ATENCAO -> 1;
            case ALERTA -> 2;
            case EMERGENCIA -> 3;
        };
    }
}
