package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.SprintBacklogDTO;
import com.umss.be_gestor.model.SprintBacklog;
import com.umss.be_gestor.service.SprintBacklogService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sprint_backlog")
public class SprintBacklogController {

    @Autowired
    private SprintBacklogService sprintBacklogService;

    @GetMapping("/all")
    public ResponseEntity<List<SprintBacklogDTO>> getAllSprintBacklogs() {
        return ResponseEntity.ok(sprintBacklogService.getAllSprintBacklogs());
    }

    @GetMapping("/full")
    public ResponseEntity<List<SprintBacklog>> getFullSprintBacklogs() {
        return ResponseEntity.ok(sprintBacklogService.getFullSprintBacklogs());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<SprintBacklogDTO> getSprintBacklogById(@PathVariable UUID id) {
        return ResponseEntity.ok(sprintBacklogService.getSprintBacklogById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<SprintBacklogDTO> createSprintBacklog(@RequestBody SprintBacklogDTO sprintBacklogDTO) {
        return ResponseEntity.status(201).body(sprintBacklogService.createSprintBacklog(sprintBacklogDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SprintBacklogDTO> updateSprintBacklog(@PathVariable UUID id, @RequestBody SprintBacklogDTO sprintBacklogDTO) {
        return ResponseEntity.ok(sprintBacklogService.updateSprintBacklog(id, sprintBacklogDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSprintBacklog(@PathVariable UUID id) {
        sprintBacklogService.deleteSprintBacklog(id);
        return ResponseEntity.noContent().build();
    }
}
