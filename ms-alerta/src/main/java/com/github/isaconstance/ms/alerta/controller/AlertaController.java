package com.github.isaconstance.ms.alerta.controller;

import com.github.isaconstance.ms.alerta.dto.AlertaRequestDTO;
import com.github.isaconstance.ms.alerta.dto.AlertaResponseDTO;
import com.github.isaconstance.ms.alerta.exceptions.DatabaseException;
import com.github.isaconstance.ms.alerta.exceptions.ResourceNotFoundException;
import com.github.isaconstance.ms.alerta.service.AlertaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/alertas")
@AllArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> getAll() {
        return ResponseEntity.ok(alertaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(alertaService.getById(id));
    }

    @PostMapping
    @CircuitBreaker(name = "atualizarIdoso",
            fallbackMethod = "fallbackCreate")
    public ResponseEntity<AlertaResponseDTO> create(
            @Valid @RequestBody AlertaRequestDTO dto) {

        AlertaResponseDTO response = alertaService.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    public ResponseEntity<AlertaResponseDTO> fallbackCreate(
            AlertaRequestDTO dto, Throwable e) {

        if (e instanceof ResourceNotFoundException
                || e instanceof DatabaseException) {
            throw (RuntimeException) e;
        }

        log.error("Falha ao comunicar com ms-idoso ao criar alerta. Ativando fallback. Erro: {}",
                e.getMessage());

        AlertaResponseDTO response = alertaService.createPendente(dto);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AlertaRequestDTO dto) {

        return ResponseEntity.ok(alertaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/acompanhar")
    public ResponseEntity<AlertaResponseDTO> acompanhar(
            @PathVariable Long id) {
        return ResponseEntity.ok(alertaService.acompanhar(id));
    }

    @PatchMapping("/{id}/resolver")
    @CircuitBreaker(name = "atualizarIdoso",
            fallbackMethod = "fallbackResolver")
    public ResponseEntity<AlertaResponseDTO> resolver(
            @PathVariable Long id) {
        return ResponseEntity.ok(alertaService.resolver(id));
    }

    public ResponseEntity<AlertaResponseDTO> fallbackResolver(
            Long id, Throwable e) {

        if (e instanceof ResourceNotFoundException
                || e instanceof DatabaseException) {
            throw (RuntimeException) e;
        }

        log.error("Falha ao comunicar com ms-idoso ao resolver alerta {}. Ativando fallback. Erro: {}",
                id, e.getMessage());

        AlertaResponseDTO response = alertaService.resolverPendente(id);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}