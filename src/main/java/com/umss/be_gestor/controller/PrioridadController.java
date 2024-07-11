package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.PrioridadDTO;
import com.umss.be_gestor.model.Prioridad;
import com.umss.be_gestor.service.PrioridadService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prioridad")
public class PrioridadController {

    @Autowired
    private PrioridadService prioridadService;

    @GetMapping("/all")
    public ResponseEntity<List<PrioridadDTO>> getAllPrioridades() {
        return ResponseEntity.ok(prioridadService.getAllPrioridades());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Prioridad>> getFullPrioridades() {
        return ResponseEntity.ok(prioridadService.getFullPrioridades());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PrioridadDTO> getPrioridadById(@PathVariable UUID id) {
        return ResponseEntity.ok(prioridadService.getPrioridadById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PrioridadDTO> createPrioridad(@RequestBody PrioridadDTO prioridadDTO) {
        return ResponseEntity.status(201).body(prioridadService.createPrioridad(prioridadDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PrioridadDTO> updatePrioridad(@PathVariable UUID id, @RequestBody PrioridadDTO prioridadDTO) {
        return ResponseEntity.ok(prioridadService.updatePrioridad(id, prioridadDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePrioridad(@PathVariable UUID id) {
        prioridadService.deletePrioridad(id);
        return ResponseEntity.noContent().build();
    }
}
