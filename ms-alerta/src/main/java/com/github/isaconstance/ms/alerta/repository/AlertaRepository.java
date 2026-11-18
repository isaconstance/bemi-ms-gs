package com.github.isaconstance.ms.alerta.repository;

import com.github.isaconstance.ms.alerta.entities.Alerta;
import com.github.isaconstance.ms.alerta.entities.StatusAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByIdosoIdAndStatusNot(Long idosoId, StatusAlerta status);
}
