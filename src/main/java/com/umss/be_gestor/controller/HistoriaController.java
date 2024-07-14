package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.HistoriaDTO;
import com.umss.be_gestor.model.Historia;
import com.umss.be_gestor.service.HistoriaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/historia")
public class HistoriaController {

    @Autowired
    private HistoriaService historiaService;

    @GetMapping("/all")
    public ResponseEntity<List<HistoriaDTO>> getAllHistorias() {
        return ResponseEntity.ok(historiaService.getAllHistorias());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Historia>> getFullHistorias() {
        return ResponseEntity.ok(historiaService.getFullHistorias());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<HistoriaDTO> getHistoriaById(@PathVariable UUID id) {
        return ResponseEntity.ok(historiaService.getHistoriaById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<HistoriaDTO> createHistoria(@RequestBody HistoriaDTO historiaDTO) {
        return ResponseEntity.status(201).body(historiaService.createHistoria(historiaDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HistoriaDTO> updateHistoria(@PathVariable UUID id, @RequestBody HistoriaDTO historiaDTO) {
        return ResponseEntity.ok(historiaService.updateHistoria(id, historiaDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHistoria(@PathVariable UUID id) {
        historiaService.deleteHistoria(id);
        return ResponseEntity.noContent().build();
    }
}
