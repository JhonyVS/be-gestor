package com.umss.be_gestor.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.ProyectoDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.service.ProyectoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/all")
    public ResponseEntity<List<ProyectoDTO>> getAllProyectos() {
        return ResponseEntity.ok(proyectoService.getAllProyectos());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Proyecto>> getFullProyectos() {
        return ResponseEntity.ok(proyectoService.getFullProyectos());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProyectoDTO> getProyectoById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(proyectoService.getProyectoById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProyectoDTO> createProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        return ResponseEntity.status(201).body(proyectoService.createProyecto(proyectoDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProyectoDTO> updateProyecto(@PathVariable UUID id, @RequestBody ProyectoDTO proyectoDTO) throws NotFoundException {
        return ResponseEntity.ok(proyectoService.updateProyecto(id, proyectoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable UUID id) throws NotFoundException {
        proyectoService.deleteProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
