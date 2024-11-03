package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umss.be_gestor.dto.TableroDTO;
import com.umss.be_gestor.dto.WorkspaceDTO;
import com.umss.be_gestor.dto.WorkspaceResponseDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Workspace;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.WorkspaceRepository;
import com.umss.be_gestor.repository.UsuarioRepository;
import com.umss.be_gestor.util.DTOConverter;

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
                .map(DTOConverter::convertToWorkspaceDTO)  // Utilizando el método estático del DTOConverter
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
        return DTOConverter.convertToWorkspaceDTO(workspace);
    }

    public WorkspaceDTO createWorkspace(WorkspaceDTO workspaceDTO) {
        Workspace workspace = DTOConverter.convertToWorkspaceEntity(workspaceDTO, usuarioRepository);
        workspace.setCreatedAt(LocalDateTime.now());
        workspace.setUpdatedAt(LocalDateTime.now());
        workspace.setActivado(true);  // Inicializar activado como true
        workspace = workspaceRepository.save(workspace);
        return DTOConverter.convertToWorkspaceDTO(workspace);
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
        return DTOConverter.convertToWorkspaceDTO(workspace);
    }

    public void deleteWorkspace(UUID id) {
        Workspace workspace = workspaceRepository.findById(id).orElse(null);
        if (workspace == null) {
            throw new NotFoundException("Workspace", id.toString());
        }
        workspaceRepository.delete(workspace);
    }
    public WorkspaceResponseDTO getWorkspaceDetailsByProjectManagerId(UUID projectManagerId) {
        Workspace workspace = workspaceRepository.findByProjectManager_Id(projectManagerId)
                .orElseThrow(() -> new NotFoundException("Workspace no encontrado para el Project Manager con ID: " + projectManagerId, null));
    
        WorkspaceResponseDTO workspaceDTO = new WorkspaceResponseDTO();
        workspaceDTO.setId(workspace.getId());
        workspaceDTO.setProjectManagerId(workspace.getProjectManager().getId());
    
        return workspaceDTO;
    }

    public WorkspaceResponseDTO getWorkspaceWithTableros(UUID projectManagerId) {
        Workspace workspace = workspaceRepository.findByProjectManager_Id(projectManagerId)
                .orElseThrow(() -> new NotFoundException("Workspace no encontrado para el Project Manager con ID: " + projectManagerId, null));
    
        // Convertir Workspace a WorkspaceResponseDTO
        WorkspaceResponseDTO workspaceDTO = new WorkspaceResponseDTO();
        workspaceDTO.setId(workspace.getId());
        workspaceDTO.setProjectManagerId(workspace.getProjectManager().getId());
    
        // Convertir cada Tablero en TableroDTO y agregarlo a la lista
        List<TableroDTO> tableroDTOs = workspace.getTableros().stream()
                .map(DTOConverter::convertToTableroDTO)
                .collect(Collectors.toList());
        workspaceDTO.setTableros(tableroDTOs);
    
        return workspaceDTO;
    }

    public WorkspaceResponseDTO getWorkspaceWithTablerosAndTarjetas(UUID projectManagerId) {
        Workspace workspace = workspaceRepository.findByProjectManager_Id(projectManagerId)
                .orElseThrow(() -> new NotFoundException("Workspace no encontrado para el Project Manager con ID: " + projectManagerId, null));
    
        WorkspaceResponseDTO workspaceResponseDTO = DTOConverter.convertToWorkspaceResponseDTO(workspace);
    
        // Convertir los tableros a DTO e incluir sus tarjetas
        workspaceResponseDTO.setTableros(workspace.getTableros().stream()
                .map(DTOConverter::convertToTableroDTO)
                .collect(Collectors.toList()));
    
        return workspaceResponseDTO;
    }
    
    
    
}
