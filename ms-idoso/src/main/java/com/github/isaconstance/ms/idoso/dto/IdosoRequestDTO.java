package com.github.isaconstance.ms.idoso.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IdosoRequestDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 100, message = "O campo nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O campo CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotNull(message = "O campo data de nascimento é obrigatório")
    @Past(message = "A data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos numéricos")
    private String telefone;

    @NotBlank(message = "O campo nome do contato de emergência é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do contato de emergência deve ter entre 3 e 100 caracteres")
    private String nomeContatoEmergencia;

    @NotBlank(message = "O campo telefone do contato de emergência é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone do contato de emergência deve conter 10 ou 11 dígitos numéricos")
    private String telefoneContatoEmergencia;
}
