package com.umss.be_gestor.util;

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
        tableroDTO.setWorkspaceId(tablero.getWorkspace().getId() != null ? tablero.getWorkspace().getId(): null);
        tableroDTO.setActivado(tablero.getActivado());
    
        
        return tableroDTO;
    }

    public static TarjetaDTO convertToTarjetaDTO(Tarjeta tarjeta) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO();
        tarjetaDTO.setId(tarjeta.getId());
        tarjetaDTO.setTitulo(tarjeta.getTitulo());
        tarjetaDTO.setDescripcion(tarjeta.getDescripcion());
        tarjetaDTO.setActivado(tarjeta.getActivado());
        
        
        return tarjetaDTO;
    }

    public static TareaDTO convertToTareaDTO(Tarea tarea) {
        TareaDTO tareaDTO = new TareaDTO();
        tareaDTO.setId(tarea.getId());
        tareaDTO.setTitulo(tarea.getTitulo());
        tareaDTO.setDescripcion(tarea.getDescripcion());
        tareaDTO.setEstimacion(tarea.getEstimacion());
        tareaDTO.setHistoriaId(tarea.getHistoria() != null ? tarea.getHistoria().getId() : null);
        tareaDTO.setTarjetaId(tarea.getTarjeta() != null ? tarea.getTarjeta().getId() : null);
        tareaDTO.setUsuarioAsignadoId(tarea.getUsuarioAsignado() != null ? tarea.getUsuarioAsignado().getId() : null);
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
}
