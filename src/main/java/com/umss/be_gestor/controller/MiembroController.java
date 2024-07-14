package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.MiembroDTO;
import com.umss.be_gestor.model.Miembro;
import com.umss.be_gestor.service.MiembroService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/miembro")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    @GetMapping("/all")
    public ResponseEntity<List<MiembroDTO>> getAllMiembros() {
        return ResponseEntity.ok(miembroService.getAllMiembros());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Miembro>> getFullMiembros() {
        return ResponseEntity.ok(miembroService.getFullMiembros());
    }

    @GetMapping("/find/{usuarioId}/{equipoId}/{rolId}")
    public ResponseEntity<MiembroDTO> getMiembroById(
            @PathVariable UUID usuarioId,
            @PathVariable UUID equipoId,
            @PathVariable UUID rolId) {
        return ResponseEntity.ok(miembroService.getMiembroById(usuarioId, equipoId, rolId));
    }

    @PostMapping("/create")
    public ResponseEntity<MiembroDTO> createMiembro(@RequestBody MiembroDTO miembroDTO) {
        return ResponseEntity.status(201).body(miembroService.createMiembro(miembroDTO));
    }

    @PutMapping("/update/{usuarioId}/{equipoId}/{rolId}")
    public ResponseEntity<MiembroDTO> updateMiembro(
            @PathVariable UUID usuarioId,
            @PathVariable UUID equipoId,
            @PathVariable UUID rolId,
            @RequestBody MiembroDTO miembroDTO) {
        return ResponseEntity.ok(miembroService.updateMiembro(usuarioId, equipoId, rolId, miembroDTO));
    }

    @DeleteMapping("/delete/{usuarioId}/{equipoId}/{rolId}")
    public ResponseEntity<Void> deleteMiembro(
            @PathVariable UUID usuarioId,
            @PathVariable UUID equipoId,
            @PathVariable UUID rolId) {
        miembroService.deleteMiembro(usuarioId, equipoId, rolId);
        return ResponseEntity.noContent().build();
    }
}
