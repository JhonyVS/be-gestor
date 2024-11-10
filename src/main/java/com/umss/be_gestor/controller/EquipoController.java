package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.EquipoDTO;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.model.Equipo;
import com.umss.be_gestor.service.EquipoService;
import com.umss.be_gestor.service.MiembroService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipo")
public class EquipoController {



    @Autowired
    private EquipoService equipoService;

    private final MiembroService miembroService;

    @Autowired
    public EquipoController(MiembroService miembroService) {
        this.miembroService = miembroService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EquipoDTO>> getAllEquipos() {
        return ResponseEntity.ok(equipoService.getAllEquipos());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Equipo>> getFullEquipos() {
        return ResponseEntity.ok(equipoService.getFullEquipos());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<EquipoDTO> getEquipoById(@PathVariable UUID id) {
        return ResponseEntity.ok(equipoService.getEquipoById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EquipoDTO> createEquipo(@RequestBody EquipoDTO equipoDTO) {
        return ResponseEntity.status(201).body(equipoService.createEquipo(equipoDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EquipoDTO> updateEquipo(@PathVariable UUID id, @RequestBody EquipoDTO equipoDTO) {
        return ResponseEntity.ok(equipoService.updateEquipo(id, equipoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable UUID id) {
        equipoService.deleteEquipo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * endpoints personalizados
     */
    @GetMapping("/{equipoId}/integrantes")
    public ResponseEntity<List<UsuarioDTO>> getIntegrantesByEquipo(@PathVariable UUID equipoId) {
        List<UsuarioDTO> integrantes = miembroService.getIntegrantesByEquipo(equipoId);
        return ResponseEntity.ok(integrantes);
    }
}
