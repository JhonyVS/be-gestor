package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.RolDTO;
import com.umss.be_gestor.model.Rol;
import com.umss.be_gestor.service.RolService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/all")
    public ResponseEntity<List<RolDTO>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAllRoles());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Rol>> getFullRoles() {
        return ResponseEntity.ok(rolService.getFullRoles());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RolDTO> getRolById(@PathVariable UUID id) {
        return ResponseEntity.ok(rolService.getRolById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RolDTO> createRol(@RequestBody RolDTO rolDTO) {
        return ResponseEntity.status(201).body(rolService.createRol(rolDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RolDTO> updateRol(@PathVariable UUID id, @RequestBody RolDTO rolDTO) {
        return ResponseEntity.ok(rolService.updateRol(id, rolDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable UUID id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}
