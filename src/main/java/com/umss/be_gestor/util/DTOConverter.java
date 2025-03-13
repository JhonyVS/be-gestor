package com.umss.be_gestor.util;


import org.springframework.stereotype.Component;

import com.umss.be_gestor.dto.*;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.*;
import com.umss.be_gestor.repository.EstadoTareaRepository;
import com.umss.be_gestor.repository.HistoriaRepository;
import com.umss.be_gestor.repository.IUsuarioRepository;
import com.umss.be_gestor.repository.ProyectoRepository;
import com.umss.be_gestor.repository.TableroRepository;
import com.umss.be_gestor.repository.TarjetaRepository;
import com.umss.be_gestor.repository.WorkspaceRepository;

import java.util.stream.Collectors;



@Component
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


    public static TableroDTO convertToDTO(Tablero tablero) {
        TableroDTO tableroDTO = new TableroDTO();
        tableroDTO.setId(tablero.getId());
        tableroDTO.setProyectoId(tablero.getProyecto() != null ? tablero.getProyecto().getId() : null);
        tableroDTO.setWorkspaceId(tablero.getWorkspace().getId() != null ? tablero.getWorkspace().getId() : null);
        tableroDTO.setTitulo(tablero.getTitulo());
        tableroDTO.setDescripcion(tablero.getDescripcion());
        tableroDTO.setActivado(tablero.getActivado());
        return tableroDTO;
    }

    public static Tablero convertToEntity(TableroDTO tableroDTO, ProyectoRepository proyectoRepository, WorkspaceRepository workspaceRepository) {
        Tablero tablero = new Tablero();

        if (tableroDTO.getProyectoId() != null) {
            Proyecto proyecto = proyectoRepository.findById(tableroDTO.getProyectoId()).orElse(null);
            if (proyecto == null) {
                throw new NotFoundException("Proyecto", tableroDTO.getProyectoId().toString());
            }
            tablero.setProyecto(proyecto);
        }

        if (tableroDTO.getWorkspaceId() != null) {
            Workspace workspace = workspaceRepository.findById(tableroDTO.getWorkspaceId()).orElse(null);
            if (workspace == null) {
                throw new NotFoundException("Workspace", tableroDTO.getWorkspaceId().toString());
            }
            tablero.setWorkspace(workspace);
        }

        tablero.setTitulo(tableroDTO.getTitulo());
        tablero.setDescripcion(tableroDTO.getDescripcion());
        tablero.setActivado(tableroDTO.getActivado());
        return tablero;
    }

    public static TarjetaDTO convertToDTO(Tarjeta tarjeta) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO();
        tarjetaDTO.setId(tarjeta.getId());
        tarjetaDTO.setTitulo(tarjeta.getTitulo());
        tarjetaDTO.setDescripcion(tarjeta.getDescripcion());
        tarjetaDTO.setTableroId(tarjeta.getTablero().getId());
        tarjetaDTO.setActivado(tarjeta.getActivado());
        return tarjetaDTO;
    }


    public static Tarjeta convertToEntity(TarjetaDTO tarjetaDTO, TableroRepository tableroRepository) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setTitulo(tarjetaDTO.getTitulo());
        tarjeta.setDescripcion(tarjetaDTO.getDescripcion());

        Tablero tablero = tableroRepository.findById(tarjetaDTO.getTableroId()).orElse(null);
        if (tablero == null) {
            throw new NotFoundException("Tablero", tarjetaDTO.getTableroId().toString());
        }
        tarjeta.setTablero(tablero);

        tarjeta.setActivado(tarjetaDTO.getActivado());
        return tarjeta;
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
        // Agregar más datos si es necesario
        if (tarea.getEstado() != null) {
            tareaDTO.setEstado(tarea.getEstado());
        }
        if (tarea.getUsuarioAsignado() != null) {
            tareaDTO.setUsuarioAsignado(DTOConverter.convertirAUsuarioBasicoDTO(tarea.getUsuarioAsignado()));
        }

        return tareaDTO;
    }
    


    // Método para convertir WorkspaceDTO a Workspace (requiere UsuarioRepository)
    public static Workspace convertToWorkspaceEntity(WorkspaceDTO workspaceDTO, IUsuarioRepository usuarioRepository) {
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

    public static ProyectoDTO convertToDTO(Proyecto proyecto) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(proyecto.getId());
        dto.setNombre(proyecto.getNombre());
        dto.setDescripcion(proyecto.getDescripcion());
        dto.setFechaInicio(proyecto.getFechaInicio());
        dto.setFechaFinal(proyecto.getFechaFinal());
        dto.setProjectManagerId(proyecto.getProjectManager().getId());
        dto.setActivado(proyecto.getActivado());
        return dto;
    }

    public static EquipoDTO convertToEquipoDTO(Equipo equipo) {
        EquipoDTO equipoDTO = new EquipoDTO();
        equipoDTO.setId(equipo.getId());
        equipoDTO.setNombre(equipo.getNombre());
        equipoDTO.setProyectoId(equipo.getProyecto().getId());
        equipoDTO.setActivado(equipo.getActivado());
    
        // Convertir miembros a UsuarioDTO
        equipoDTO.setIntegrantes(
            equipo.getMiembros().stream()
                .map(miembro -> convertirAUsuarioBasicoDTO(miembro.getUsuario()))
                .collect(Collectors.toList())
        );
    
        return equipoDTO;
    }

    public static TableroDTO convertirTableroADTO(Tablero tablero) {
        TableroDTO tableroDTO = new TableroDTO();
        tableroDTO.setId(tablero.getId());
        if(tablero.getProyecto() != null)
            tableroDTO.setProyectoId(tablero.getProyecto().getId());
        if(tablero.getWorkspace() != null)
            tableroDTO.setWorkspaceId(tablero.getWorkspace().getId());
        tableroDTO.setTitulo(tablero.getTitulo());
        tableroDTO.setDescripcion(tablero.getDescripcion());
        tableroDTO.setActivado(tablero.getActivado());

        return tableroDTO;
    }    
    
    
    
    
    public static UsuarioDTO convertToUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombres(usuario.getNombres());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        return usuarioDTO;
    }

    public static ProyectoDTO convertToProyectoDTO(Proyecto proyecto) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(proyecto.getId());
        dto.setNombre(proyecto.getNombre());
        dto.setDescripcion(proyecto.getDescripcion());
        dto.setFechaInicio(proyecto.getFechaInicio());
        dto.setFechaFinal(proyecto.getFechaFinal());
        dto.setProjectManagerId(proyecto.getProjectManager().getId());
        dto.setActivado(proyecto.getActivado());
        return dto;
    }

    public static ComentarioDTO convertirADTO(Comentario comentario, UsuarioBasicoDTO usuarioBasicoDTO) {
        return new ComentarioDTO(
            comentario.getId(),
            usuarioBasicoDTO, // Usuario en formato UsuarioBasicDTO
            comentario.getProyecto().getId(),
            comentario.getContenido(),
            comentario.getCreatedAt()
        );
    }
    
    
    public static Comentario convertirADominio(ComentarioDTO comentarioDTO, IUsuarioRepository usuarioRepository) {
        Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
    
        Proyecto proyecto = new Proyecto();
        proyecto.setId(comentarioDTO.getIdProyecto());
        comentario.setProyecto(proyecto);
        Usuario usuario = usuarioRepository.findById(comentarioDTO.getUsuario().getId()).orElse(null);
        if (usuario == null) {
            throw new NotFoundException("Usuario", comentarioDTO.getIdProyecto().toString());
        }
        comentario.setUsuario(usuario);
        comentario.setContenido(comentarioDTO.getContenido());
        return comentario;
    }
    
    // Conversión de Evento a EventoDTO
    public static EventoDTO convertirADTO(Evento evento) {
        if (evento == null) {
            return null;
        }
        return new EventoDTO(
                evento.getId(),
                evento.getProyecto().getId(),
                evento.getTitulo(),
                evento.getDescripcion(),
                evento.getFechaInicio(),
                evento.getFechaFin()
        );
    }

    // Conversión de EventoDTO a Evento
    public static Evento convertirADominio(EventoDTO eventoDTO, ProyectoRepository proyectoRepository) {
        if (eventoDTO == null) {
            return null;
        }
        Evento evento = new Evento();
        evento.setId(eventoDTO.getId());
        Proyecto proyecto = proyectoRepository.findById(eventoDTO.getIdProyecto()).orElse(null);
        if(proyecto == null){
            throw new NotFoundException("Proyecto", eventoDTO.getIdProyecto().toString());
        }
        evento.setProyecto(proyecto);
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        return evento;
    }

    public static UsuarioBasicoDTO convertirAUsuarioBasicoDTO(Usuario usuario) {
        UsuarioBasicoDTO usuarioBasicoDTO = null;
        if (usuario != null) {
            usuarioBasicoDTO =  new UsuarioBasicoDTO();
            usuarioBasicoDTO.setId(usuario.getId());
            usuarioBasicoDTO.setNombres(usuario.getNombres());
            usuarioBasicoDTO.setApellidos(usuario.getApellidos());
            usuarioBasicoDTO.setUsername(usuario.getApellidos());
            usuarioBasicoDTO.setEmail(usuario.getEmail());
            usuarioBasicoDTO.setFechaNacimiento(usuario.getNacimiento().toString());
            usuarioBasicoDTO.setTelefono(usuario.getTelefono());
                
        }
        return usuarioBasicoDTO;

    }

    
    public static TareaDTO convertToDTO(Tarea tarea) {
        TareaDTO tareaDTO = new TareaDTO();
        tareaDTO.setId(tarea.getId());
        tareaDTO.setTitulo(tarea.getTitulo());
        tareaDTO.setDescripcion(tarea.getDescripcion());
        tareaDTO.setEstimacion(tarea.getEstimacion());
        tareaDTO.setEstado(tarea.getEstado());
        tareaDTO.setHistoriaId(tarea.getHistoria() != null ? tarea.getHistoria().getId() : null);
        tareaDTO.setTarjetaId(tarea.getTarjeta() != null ? tarea.getTarjeta().getId() : null);
        tareaDTO.setUsuarioAsignado(convertirAUsuarioBasicoDTO(tarea.getUsuarioAsignado()));
        tareaDTO.setActivado(tarea.getActivado());
        return tareaDTO;
    }

    public static Tarea convertToEntity(TareaDTO tareaDTO,HistoriaRepository historiaRepository, TarjetaRepository tarjetaRepository,IUsuarioRepository usuarioRepository,EstadoTareaRepository estadoTareaRepository) {
        Tarea tarea = new Tarea();

        tarea.setId(tareaDTO.getId());
        tarea.setTitulo(tareaDTO.getTitulo());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setEstimacion(tareaDTO.getEstimacion());
        // Establecer el estado
        if (tareaDTO.getEstado() != null) {
            tarea.setEstado(tareaDTO.getEstado());
        } else {
            tarea.setEstado(estadoTareaRepository.findByNombre("Pendiente").orElse(null));
        }
        if(tareaDTO.getHistoriaId() != null)
            tarea.setHistoria(historiaRepository.findById(tareaDTO.getHistoriaId()).orElse(null));
        tarea.setTarjeta(tarjetaRepository.findById(tareaDTO.getTarjetaId()).orElse(null));
        if(tareaDTO.getUsuarioAsignado() != null)
            tarea.setUsuarioAsignado(usuarioRepository.findById(tareaDTO.getUsuarioAsignado().getId()).orElse(null));
        //tarea.setActivado(tareaDTO.getActivado());
        return tarea;
    }

    public static EstadoTareaDTO convertToDTO(EstadoTarea estadoTarea) {
        return new EstadoTareaDTO(estadoTarea.getId(), estadoTarea.getNombre());
    }

    public static EstadoTarea convertoToEntity(EstadoTareaDTO dto) {
        EstadoTarea estadoTarea = new EstadoTarea();
        estadoTarea.setId(dto.getId());
        estadoTarea.setNombre(dto.getNombre());
        return estadoTarea;
    }
    
    
    
    public static WorkspaceDTO convertirWorkspaceADTO(Workspace workspace) {
        return new WorkspaceDTO(
            workspace.getId(),
            workspace.getProjectManager().getId(),
            workspace.getActivado()
        );
    }


    public static EquipoDTO convertToDTO(Equipo equipo) {
        EquipoDTO equipoDTO = new EquipoDTO();
        equipoDTO.setId(equipo.getId());
        equipoDTO.setNombre(equipo.getNombre());
        if(equipo.getProyecto()!=null)
            equipoDTO.setProyectoId(equipo.getProyecto().getId());
        if(equipo.getCapitan() != null)
            equipoDTO.setUsuarioCapitan(DTOConverter.convertirAUsuarioBasicoDTO(equipo.getCapitan()));
        equipoDTO.setActivado(equipo.getActivado());
        return equipoDTO;
    }

    public static Equipo convertToEntity(EquipoDTO equipoDTO, ProyectoRepository proyectoRepository, IUsuarioRepository usuarioRepository) {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        if(equipoDTO.getProyectoId() != null){
            Proyecto proyecto = proyectoRepository.findById(equipoDTO.getProyectoId()).orElse(null);
            equipo.setProyecto(proyecto);
        }
        Usuario usuario = usuarioRepository.findById(equipoDTO.getUsuarioCapitan().getId()).orElse(null);
        if(usuario != null)
            equipo.setCapitan(usuario);
        equipo.setActivado(equipoDTO.getActivado());
        return equipo;
    }
    


}
