package com.github.isaconstance.ms.idoso.service;

import com.github.isaconstance.ms.idoso.dto.IdosoRequestDTO;
import com.github.isaconstance.ms.idoso.dto.IdosoResponseDTO;
import com.github.isaconstance.ms.idoso.entities.Idoso;
import com.github.isaconstance.ms.idoso.entities.Status;
import com.github.isaconstance.ms.idoso.exceptions.DatabaseException;
import com.github.isaconstance.ms.idoso.exceptions.ResourceNotFoundException;
import com.github.isaconstance.ms.idoso.repository.IdosoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IdosoService {

    private final IdosoRepository idosoRepository;

    public IdosoService(IdosoRepository idosoRepository) {
        this.idosoRepository = idosoRepository;
    }

    @Transactional(readOnly = true)
    public List<IdosoResponseDTO> findAllIdosos() {

        return idosoRepository.findAll()
                .stream()
                .map(IdosoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public IdosoResponseDTO findIdosoById(Long id) {

        Idoso idoso = getIdosoOrThrow(id);

        return new IdosoResponseDTO(idoso);
    }

    @Transactional
    public IdosoResponseDTO saveIdoso(IdosoRequestDTO requestDTO) {

        if (idosoRepository.existsByCpf(requestDTO.getCpf())) {
            throw new DatabaseException("CPF já cadastrado: " + requestDTO.getCpf());
        }

        Idoso idoso = new Idoso();
        mapDtoToIdoso(requestDTO, idoso);
        idoso.setStatus(Status.ESTAVEL);
        idoso = idosoRepository.save(idoso);

        return new IdosoResponseDTO(idoso);
    }

    @Transactional
    public IdosoResponseDTO updateIdoso(Long id, IdosoRequestDTO requestDTO) {

        Idoso idoso = getIdosoOrThrow(id);

        if (!idoso.getCpf().equals(requestDTO.getCpf())
                && idosoRepository.existsByCpf(requestDTO.getCpf())) {
            throw new DatabaseException("CPF já cadastrado: " + requestDTO.getCpf());
        }
        mapDtoToIdoso(requestDTO, idoso);
        idoso = idosoRepository.save(idoso);

        return new IdosoResponseDTO(idoso);
    }

    @Transactional
    public void deleteIdosoById(Long id) {

        if (!idosoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        idosoRepository.deleteById(id);
    }

    @Transactional
    public IdosoResponseDTO updateStatus(Long id, Status status) {

        Idoso idoso = getIdosoOrThrow(id);
        idoso.setStatus(status);
        idoso = idosoRepository.save(idoso);

        return new IdosoResponseDTO(idoso);
    }

    private Idoso getIdosoOrThrow(Long id) {

        return idosoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );
    }

    private void mapDtoToIdoso(IdosoRequestDTO requestDTO, Idoso idoso) {

        idoso.setNome(requestDTO.getNome());
        idoso.setCpf(requestDTO.getCpf());
        idoso.setDataNascimento(requestDTO.getDataNascimento());
        idoso.setTelefone(requestDTO.getTelefone());
        idoso.setNomeContatoEmergencia(requestDTO.getNomeContatoEmergencia());
        idoso.setTelefoneContatoEmergencia(requestDTO.getTelefoneContatoEmergencia());
    }
}
