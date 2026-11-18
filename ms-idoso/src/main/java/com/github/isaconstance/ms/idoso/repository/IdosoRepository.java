package com.github.isaconstance.ms.idoso.repository;

import com.github.isaconstance.ms.idoso.entities.Idoso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdosoRepository extends JpaRepository<Idoso, Long> {
    boolean existsByCpf(String cpf);
}
