package com.github.isaconstance.ms.idoso.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_idoso")
public class Idoso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 11)
    private String telefone;

    @Column(nullable = false, length = 100)
    private String nomeContatoEmergencia;

    @Column(nullable = false, length = 11)
    private String telefoneContatoEmergencia;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;
}
