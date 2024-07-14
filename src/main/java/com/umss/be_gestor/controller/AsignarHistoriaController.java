package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.AsignarHistoriaDTO;
import com.umss.be_gestor.model.AsignarHistoria;
import com.umss.be_gestor.service.AsignarHistoriaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/asignar_historia")
public class AsignarHistoriaController {

    @Autowired
    private AsignarHistoriaService asignarHistoriaService;

    @GetMapping("/all")
    public ResponseEntity<List<AsignarHistoriaDTO>> getAllAsignarHistorias() {
        return ResponseEntity.ok(asignarHistoriaService.getAllAsignarHistorias());
    }

    @GetMapping("/full")
    public ResponseEntity<List<AsignarHistoria>> getFullAsignarHistorias() {
        return ResponseEntity.ok(asignarHistoriaService.getFullAsignarHistorias());
    }

    @GetMapping("/find/{sprintBacklogId}/{historiaId}")
    public ResponseEntity<AsignarHistoriaDTO> getAsignarHistoriaById(
            @PathVariable UUID sprintBacklogId,
            @PathVariable UUID historiaId) {
        return ResponseEntity.ok(asignarHistoriaService.getAsignarHistoriaById(sprintBacklogId, historiaId));
    }

    @PostMapping("/create")
    public ResponseEntity<AsignarHistoriaDTO> createAsignarHistoria(@RequestBody AsignarHistoriaDTO asignarHistoriaDTO) {
        return ResponseEntity.status(201).body(asignarHistoriaService.createAsignarHistoria(asignarHistoriaDTO));
    }

    @PutMapping("/update/{sprintBacklogId}/{historiaId}")
    public ResponseEntity<AsignarHistoriaDTO> updateAsignarHistoria(
            @PathVariable UUID sprintBacklogId,
            @PathVariable UUID historiaId,
            @RequestBody AsignarHistoriaDTO asignarHistoriaDTO) {
        return ResponseEntity.ok(asignarHistoriaService.updateAsignarHistoria(sprintBacklogId, historiaId, asignarHistoriaDTO));
    }

    @DeleteMapping("/delete/{sprintBacklogId}/{historiaId}")
    public ResponseEntity<Void> deleteAsignarHistoria(
            @PathVariable UUID sprintBacklogId,
            @PathVariable UUID historiaId) {
        asignarHistoriaService.deleteAsignarHistoria(sprintBacklogId, historiaId);
        return ResponseEntity.noContent().build();
    }
}
