package com.github.isaconstance.ms.alerta.dto;

import com.github.isaconstance.ms.alerta.entities.Gravidade;
import com.github.isaconstance.ms.alerta.entities.Origem;
import com.github.isaconstance.ms.alerta.entities.TipoAlerta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlertaRequestDTO {

    @NotNull(message = "O campo idosoId é obrigatório")
    private Long idosoId;

    @NotNull(message = "O campo tipo é obrigatório")
    private TipoAlerta tipo;

    @NotNull(message = "O campo gravidade é obrigatório")
    private Gravidade gravidade;

    @NotNull(message = "O campo origem é obrigatório")
    private Origem origem;

    @NotBlank(message = "O campo mensagem é obrigatório")
    @Size(min = 5, max = 500, message = "A mensagem deve ter entre 5 e 500 caracteres")
    private String mensagem;
}
