package com.github.isaconstance.ms.idoso.controller;

import com.github.isaconstance.ms.idoso.dto.IdosoRequestDTO;
import com.github.isaconstance.ms.idoso.dto.IdosoResponseDTO;
import com.github.isaconstance.ms.idoso.entities.Status;
import com.github.isaconstance.ms.idoso.service.IdosoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/idosos")
public class IdosoController {

    private final IdosoService idosoService;

    public IdosoController(IdosoService idosoService) {
        this.idosoService = idosoService;
    }

    @GetMapping
    public ResponseEntity<List<IdosoResponseDTO>> getAll() {

        return ResponseEntity.ok(idosoService.findAllIdosos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdosoResponseDTO> getOne(@PathVariable Long id) {

        return ResponseEntity.ok(idosoService.findIdosoById(id));
    }

    @PostMapping
    public ResponseEntity<IdosoResponseDTO> saveIdoso(@RequestBody @Valid IdosoRequestDTO requestDTO) {

        IdosoResponseDTO responseDTO = idosoService.saveIdoso(requestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdosoResponseDTO> updateIdoso(@PathVariable Long id,
                                                        @RequestBody @Valid IdosoRequestDTO requestDTO) {

        return ResponseEntity.ok(idosoService.updateIdoso(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdoso(@PathVariable Long id) {

        idosoService.deleteIdosoById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<IdosoResponseDTO> updateStatus(@PathVariable Long id,
                                                         @RequestParam Status status) {

        return ResponseEntity.ok(idosoService.updateStatus(id, status));
    }
}