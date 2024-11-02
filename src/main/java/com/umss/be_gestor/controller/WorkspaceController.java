package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.WorkspaceDTO;
import com.umss.be_gestor.dto.WorkspaceResponseDTO;
import com.umss.be_gestor.model.Workspace;
import com.umss.be_gestor.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/all")
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.getAllWorkspaces());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Workspace>> getFullWorkspaces() {
        return ResponseEntity.ok(workspaceService.getFullWorkspaces());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<WorkspaceDTO> getWorkspaceById(@PathVariable UUID id) {
        return ResponseEntity.ok(workspaceService.getWorkspaceById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody WorkspaceDTO workspaceDTO) {
        return ResponseEntity.status(201).body(workspaceService.createWorkspace(workspaceDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkspaceDTO> updateWorkspace(@PathVariable UUID id, @RequestBody WorkspaceDTO workspaceDTO) {
        return ResponseEntity.ok(workspaceService.updateWorkspace(id, workspaceDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable UUID id) {
        workspaceService.deleteWorkspace(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-project-manager/{projectManagerId}")
    public ResponseEntity<Workspace> getWorkspaceDetailsByProjectManagerId(@PathVariable UUID projectManagerId) {
        Workspace workspace = workspaceService.getWorkspaceWithTableros(projectManagerId);
        return ResponseEntity.ok(workspace);
    }

}
