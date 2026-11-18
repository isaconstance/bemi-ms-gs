package com.github.isaconstance.ms.alerta.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IdosoResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String nomeContatoEmergencia;
    private String telefoneContatoEmergencia;
    private StatusIdoso status;
}