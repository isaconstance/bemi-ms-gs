package com.github.isaconstance.ms.idoso.dto;

import com.github.isaconstance.ms.idoso.entities.Idoso;
import com.github.isaconstance.ms.idoso.entities.Status;
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
    private Status status;

    public IdosoResponseDTO(Idoso idoso) {
        id = idoso.getId();
        nome = idoso.getNome();
        cpf = idoso.getCpf();
        dataNascimento = idoso.getDataNascimento();
        telefone = idoso.getTelefone();
        nomeContatoEmergencia = idoso.getNomeContatoEmergencia();
        telefoneContatoEmergencia = idoso.getTelefoneContatoEmergencia();
        status = idoso.getStatus();
    }
}
