package com.github.isaconstance.ms.alerta.dto;

import com.github.isaconstance.ms.alerta.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlertaResponseDTO {

    private Long id;
    private Long idosoId;
    private TipoAlerta tipo;
    private Gravidade gravidade;
    private Origem origem;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private StatusAlerta status;
    private boolean statusSincronizado;

    public AlertaResponseDTO(Alerta alerta) {
        id = alerta.getId();
        idosoId = alerta.getIdosoId();
        tipo = alerta.getTipo();
        gravidade = alerta.getGravidade();
        origem = alerta.getOrigem();
        mensagem = alerta.getMensagem();
        dataCriacao = alerta.getDataCriacao();
        status = alerta.getStatus();
        statusSincronizado = alerta.isStatusSincronizado();
    }
}
