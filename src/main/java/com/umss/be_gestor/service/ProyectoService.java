package com.umss.be_gestor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umss.be_gestor.dto.EquipoDTO;
import com.umss.be_gestor.dto.ProyectoDTO;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Equipo;
import com.umss.be_gestor.model.Miembro;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.MiembroRepository;
import com.umss.be_gestor.repository.ProyectoRepository;
import com.umss.be_gestor.repository.UsuarioRepository;
import com.umss.be_gestor.util.DTOConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;



    public List<ProyectoDTO> getAllProyectos() {
        return proyectoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Proyecto> getFullProyectos() {
        return proyectoRepository.findAll();
    }


    @Autowired
    public ProyectoService(MiembroRepository miembroRepository) {
        this.miembroRepository = miembroRepository;
        
    }

    public ProyectoDTO getProyectoById(UUID id) {
        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        if (proyecto == null) {
            throw new NotFoundException("Proyecto", id.toString());
        }
        return convertToDTO(proyecto);
    }

    public ProyectoDTO createProyecto(ProyectoDTO proyectoDTO) {
        Proyecto proyecto = convertToEntity(proyectoDTO);
        proyecto.setCreatedAt(LocalDateTime.now());
        proyecto.setUpdatedAt(LocalDateTime.now());
        proyecto.setActivado(true); // Inicializar activado como true
        proyecto = proyectoRepository.save(proyecto);
        return convertToDTO(proyecto);
    }

    public ProyectoDTO updateProyecto(UUID id, ProyectoDTO proyectoDTO) {
        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        if (proyecto == null) {
            throw new NotFoundException("Proyecto", id.toString());
        }

        // Actualización parcial de los campos
        if (proyectoDTO.getNombre() != null) {
            proyecto.setNombre(proyectoDTO.getNombre());
        }
        if (proyectoDTO.getDescripcion() != null) {
            proyecto.setDescripcion(proyectoDTO.getDescripcion());
        }
        if (proyectoDTO.getFechaInicio() != null) {
            proyecto.setFechaInicio(proyectoDTO.getFechaInicio());
        }
        if (proyectoDTO.getFechaFinal() != null) {
            proyecto.setFechaFinal(proyectoDTO.getFechaFinal());
        }
        if (proyectoDTO.getProjectManagerId() != null) {
            if (proyectoDTO.getProjectManagerId() != null) {
                Usuario projectManager = usuarioRepository.findById(proyectoDTO.getProjectManagerId()).orElse(null);
                if (projectManager == null) {
                    throw new NotFoundException("Usuario", proyectoDTO.getProjectManagerId().toString());
                }
                proyecto.setProjectManager(projectManager);
            }
        }
        if (proyectoDTO.getActivado() != null) {
            proyecto.setActivado(proyectoDTO.getActivado());
        }

        proyecto.setUpdatedAt(LocalDateTime.now());
        proyecto = proyectoRepository.save(proyecto);
        return convertToDTO(proyecto);
    }

    public void deleteProyecto(UUID id) {
        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        if (proyecto == null) {
            throw new NotFoundException("Proyecto", id.toString());
        }
        proyectoRepository.delete(proyecto);
    }

    private ProyectoDTO convertToDTO(Proyecto proyecto) {
        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setId(proyecto.getId());
        proyectoDTO.setNombre(proyecto.getNombre());
        proyectoDTO.setDescripcion(proyecto.getDescripcion());
        proyectoDTO.setFechaInicio(proyecto.getFechaInicio());
        proyectoDTO.setFechaFinal(proyecto.getFechaFinal());
        proyectoDTO.setProjectManagerId(proyecto.getProjectManager().getId());
        proyectoDTO.setActivado(proyecto.getActivado());
        return proyectoDTO;
    }

    private Proyecto convertToEntity(ProyectoDTO proyectoDTO) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setFechaInicio(proyectoDTO.getFechaInicio());
        proyecto.setFechaFinal(proyectoDTO.getFechaFinal());
        if (proyectoDTO.getProjectManagerId() != null) {
            Usuario projectManager = usuarioRepository.findById(proyectoDTO.getProjectManagerId()).orElse(null);
            if (projectManager == null) {
                throw new NotFoundException("Usuario", proyectoDTO.getProjectManagerId().toString());
            }
            proyecto.setProjectManager(projectManager);
        }
        proyecto.setActivado(proyectoDTO.getActivado());
        return proyecto;
    }


    public List<ProyectoDTO> getProyectosPorProjectManager(UUID projectManagerId) {
        return proyectoRepository.findByProjectManagerId(projectManagerId)
                .stream()
                .map(DTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProyectoDTO> getProyectosByProjectManager(UUID projectManagerId) {
        List<Proyecto> proyectos = proyectoRepository.findAllByProjectManagerId(projectManagerId);
    
        if (proyectos.isEmpty()) {
            throw new NotFoundException("No se encontraron proyectos para el Project Manager con ID: " + projectManagerId, null);
        }
    
        return proyectos.stream()
                .map(DTOConverter::convertToProyectoDTO)
                .collect(Collectors.toList());
    }



    public List<ProyectoDTO> getProyectosConEquiposEIntegrantesPorUsuario(UUID usuarioId) {
        // Obtiene todos los miembros donde el usuario es parte
        List<Miembro> miembros = miembroRepository.findByUsuario_Id(usuarioId);

        // Agrupa los equipos por proyecto
        Map<Proyecto, List<Equipo>> proyectosConEquipos = miembros.stream()
                .collect(Collectors.groupingBy(
                        miembro -> miembro.getEquipo().getProyecto(),
                        Collectors.mapping(Miembro::getEquipo, Collectors.toList())
                ));

        // Convierte cada proyecto y sus equipos e integrantes a DTO
        return proyectosConEquipos.entrySet().stream().map(entry -> {
            Proyecto proyecto = entry.getKey();
            List<Equipo> equipos = entry.getValue();

            ProyectoDTO proyectoDTO = DTOConverter.convertToProyectoDTO(proyecto);
            proyectoDTO.setEquipos(equipos.stream().map(equipo -> {
                EquipoDTO equipoDTO = DTOConverter.convertToEquipoDTO(equipo);

                // Agrega los integrantes del equipo
                List<UsuarioDTO> integrantes = miembros.stream()
                        .filter(miembro -> miembro.getEquipo().getId().equals(equipo.getId()))
                        .map(miembro -> DTOConverter.convertToUsuarioDTO(miembro.getUsuario()))
                        .collect(Collectors.toList());

                equipoDTO.setIntegrantes(integrantes);
                return equipoDTO;
            }).collect(Collectors.toList()));

            return proyectoDTO;
        }).collect(Collectors.toList());
    }
}
