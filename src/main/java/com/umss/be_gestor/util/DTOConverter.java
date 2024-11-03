package com.umss.be_gestor.util;

import java.util.stream.Collectors;

import com.umss.be_gestor.dto.*;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.*;
import com.umss.be_gestor.repository.UsuarioRepository;

public class DTOConverter {

     // Método para convertir Workspace a WorkspaceDTO
     public static WorkspaceDTO convertToWorkspaceDTO(Workspace workspace) {
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        workspaceDTO.setId(workspace.getId());
        workspaceDTO.setProjectManagerId(workspace.getProjectManager().getId());
        workspaceDTO.setActivado(workspace.getActivado());
        return workspaceDTO;
    }

    public static TableroDTO convertToTableroDTO(Tablero tablero) {
        TableroDTO tableroDTO = new TableroDTO();
        tableroDTO.setId(tablero.getId());
        tableroDTO.setTitulo(tablero.getTitulo());
        tableroDTO.setDescripcion(tablero.getDescripcion());
        tableroDTO.setProyectoId(tablero.getProyecto() != null ? tablero.getProyecto().getId() : null);
        tableroDTO.setWorkspaceId(tablero.getWorkspace().getId());
        tableroDTO.setActivado(tablero.getActivado());
    
        // Convertir y asignar la lista de tarjetas
        if (tablero.getTarjetas() != null) {
            tableroDTO.setTarjetas(tablero.getTarjetas().stream()
                    .map(DTOConverter::convertToTarjetaDTO)
                    .collect(Collectors.toList()));
        }
    
        return tableroDTO;
    }
    

    public static TarjetaDTO convertToTarjetaDTO(Tarjeta tarjeta) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO();
        tarjetaDTO.setId(tarjeta.getId());
        tarjetaDTO.setTableroId(tarjeta.getTablero().getId());
        tarjetaDTO.setTitulo(tarjeta.getTitulo());
        tarjetaDTO.setDescripcion(tarjeta.getDescripcion());
        tarjetaDTO.setActivado(tarjeta.getActivado());
    
        // Convertir las tareas asociadas y agregarlas al DTO
        tarjetaDTO.setTareas(tarjeta.getTareas().stream()
            .map(DTOConverter::convertToTareaDTO)
            .collect(Collectors.toList()));
    
        return tarjetaDTO;
    }
    
    

    public static TareaDTO convertToTareaDTO(Tarea tarea) {
        TareaDTO tareaDTO = new TareaDTO();
        tareaDTO.setId(tarea.getId());
        tareaDTO.setTarjetaId(tarea.getTarjeta() != null ? tarea.getTarjeta().getId() : null);
        tareaDTO.setTitulo(tarea.getTitulo());
        tareaDTO.setDescripcion(tarea.getDescripcion());
        tareaDTO.setEstimacion(tarea.getEstimacion());
        tareaDTO.setActivado(tarea.getActivado());
        return tareaDTO;
    }
    


    // Método para convertir WorkspaceDTO a Workspace (requiere UsuarioRepository)
    public static Workspace convertToWorkspaceEntity(WorkspaceDTO workspaceDTO, UsuarioRepository usuarioRepository) {
        Workspace workspace = new Workspace();

        Usuario projectManager = usuarioRepository.findById(workspaceDTO.getProjectManagerId()).orElse(null);
        if (projectManager == null) {
            throw new NotFoundException("Usuario", workspaceDTO.getProjectManagerId().toString());
        }
        workspace.setProjectManager(projectManager);
        workspace.setActivado(workspaceDTO.getActivado());
        return workspace;
    }

    public static WorkspaceResponseDTO convertToWorkspaceResponseDTO(Workspace workspace) {
        WorkspaceResponseDTO workspaceResponseDTO = new WorkspaceResponseDTO();
        workspaceResponseDTO.setId(workspace.getId());
        workspaceResponseDTO.setProjectManagerId(workspace.getProjectManager().getId());
        workspaceResponseDTO.setTableros(workspace.getTableros().stream()
            .map(DTOConverter::convertToTableroDTO)
            .collect(Collectors.toList()));
        return workspaceResponseDTO;
    }


}
