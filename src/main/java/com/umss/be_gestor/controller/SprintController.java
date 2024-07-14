package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.SprintDTO;
import com.umss.be_gestor.model.Sprint;
import com.umss.be_gestor.service.SprintService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sprint")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @GetMapping("/all")
    public ResponseEntity<List<SprintDTO>> getAllSprints() {
        return ResponseEntity.ok(sprintService.getAllSprints());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Sprint>> getFullSprints() {
        return ResponseEntity.ok(sprintService.getFullSprints());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<SprintDTO> getSprintById(@PathVariable UUID id) {
        return ResponseEntity.ok(sprintService.getSprintById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDTO) {
        return ResponseEntity.status(201).body(sprintService.createSprint(sprintDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SprintDTO> updateSprint(@PathVariable UUID id, @RequestBody SprintDTO sprintDTO) {
        return ResponseEntity.ok(sprintService.updateSprint(id, sprintDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable UUID id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }
}
