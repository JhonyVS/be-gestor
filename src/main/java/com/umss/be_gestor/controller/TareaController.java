package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.TareaDTO;
import com.umss.be_gestor.model.Tarea;
import com.umss.be_gestor.service.TareaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tarea")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping("/all")
    public ResponseEntity<List<TareaDTO>> getAllTareas() {
        return ResponseEntity.ok(tareaService.getAllTareas());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Tarea>> getFullTareas() {
        return ResponseEntity.ok(tareaService.getFullTareas());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TareaDTO> getTareaById(@PathVariable UUID id) {
        return ResponseEntity.ok(tareaService.getTareaById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TareaDTO> createTarea(@RequestBody TareaDTO tareaDTO) {
        return ResponseEntity.status(201).body(tareaService.createTarea(tareaDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TareaDTO> updateTarea(@PathVariable UUID id, @RequestBody TareaDTO tareaDTO) {
        return ResponseEntity.ok(tareaService.updateTarea(id, tareaDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable UUID id) {
        tareaService.deleteTarea(id);
        return ResponseEntity.noContent().build();
    }
}
