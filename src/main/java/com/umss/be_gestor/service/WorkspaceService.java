package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.WorkspaceDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Workspace;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.WorkspaceRepository;
import com.umss.be_gestor.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<WorkspaceDTO> getAllWorkspaces() {
        return workspaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Workspace> getFullWorkspaces() {
        return workspaceRepository.findAll();
    }

    public WorkspaceDTO getWorkspaceById(UUID id) {
        Workspace workspace = workspaceRepository.findById(id).orElse(null);
        if (workspace == null) {
            throw new NotFoundException("Workspace", id.toString());
        }
        return convertToDTO(workspace);
    }

    public WorkspaceDTO createWorkspace(WorkspaceDTO workspaceDTO) {
        Workspace workspace = convertToEntity(workspaceDTO);
        workspace.setCreatedAt(LocalDateTime.now());
        workspace.setUpdatedAt(LocalDateTime.now());
        workspace.setActivado(true); // Inicializar activado como true
        workspace = workspaceRepository.save(workspace);
        return convertToDTO(workspace);
    }

    public WorkspaceDTO updateWorkspace(UUID id, WorkspaceDTO workspaceDTO) {
        Workspace workspace = workspaceRepository.findById(id).orElse(null);
        if (workspace == null) {
            throw new NotFoundException("Workspace", id.toString());
        }

        if (workspaceDTO.getProjectManagerId() != null) {
            Usuario projectManager = usuarioRepository.findById(workspaceDTO.getProjectManagerId()).orElse(null);
            if (projectManager == null) {
                throw new NotFoundException("Usuario", workspaceDTO.getProjectManagerId().toString());
            }
            workspace.setProjectManager(projectManager);
        }
        if (workspaceDTO.getActivado() != null) {
            workspace.setActivado(workspaceDTO.getActivado());
        }

        workspace.setUpdatedAt(LocalDateTime.now());
        workspace = workspaceRepository.save(workspace);
        return convertToDTO(workspace);
    }

    public void deleteWorkspace(UUID id) {
        Workspace workspace = workspaceRepository.findById(id).orElse(null);
        if (workspace == null) {
            throw new NotFoundException("Workspace", id.toString());
        }
        workspaceRepository.delete(workspace);
    }

    private WorkspaceDTO convertToDTO(Workspace workspace) {
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        workspaceDTO.setId(workspace.getId());
        workspaceDTO.setProjectManagerId(workspace.getProjectManager().getId());
        workspaceDTO.setActivado(workspace.getActivado());
        return workspaceDTO;
    }

    private Workspace convertToEntity(WorkspaceDTO workspaceDTO) {
        Workspace workspace = new Workspace();

        Usuario projectManager = usuarioRepository.findById(workspaceDTO.getProjectManagerId()).orElse(null);
        if (projectManager == null) {
            throw new NotFoundException("Usuario", workspaceDTO.getProjectManagerId().toString());
        }
        workspace.setProjectManager(projectManager);

        workspace.setActivado(workspaceDTO.getActivado());
        return workspace;
    }
}
