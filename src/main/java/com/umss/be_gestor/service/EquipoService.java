package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.EquipoDTO;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Equipo;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.EquipoRepository;
import com.umss.be_gestor.repository.ProyectoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;



    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;

    }

    public List<EquipoDTO> getAllEquipos() {
        return equipoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Equipo> getFullEquipos() {
        return equipoRepository.findAll();
    }

    public EquipoDTO getEquipoById(UUID id) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);
        if (equipo == null) {
            throw new NotFoundException("Equipo", id.toString());
        }
        return convertToDTO(equipo);
    }

    public EquipoDTO createEquipo(EquipoDTO equipoDTO) {
        Equipo equipo = convertToEntity(equipoDTO);
        equipo.setCreatedAt(LocalDateTime.now());
        equipo.setUpdatedAt(LocalDateTime.now());
        equipo.setActivado(true); // Inicializar activado como true
        equipo = equipoRepository.save(equipo);
        return convertToDTO(equipo);
    }

    public EquipoDTO updateEquipo(UUID id, EquipoDTO equipoDTO) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);
        if (equipo == null) {
            throw new NotFoundException("Equipo", id.toString());
        }

        // Actualización parcial de los campos
        if (equipoDTO.getNombre() != null) {
            equipo.setNombre(equipoDTO.getNombre());
        }
        if (equipoDTO.getProyectoId() != null) {
            Proyecto proyecto = proyectoRepository.findById(equipoDTO.getProyectoId()).orElse(null);
            if (proyecto == null) {
                throw new NotFoundException("Proyecto", equipoDTO.getProyectoId().toString());
            }
            equipo.setProyecto(proyecto);
        }
        if (equipoDTO.getActivado() != null) {
            equipo.setActivado(equipoDTO.getActivado());
        }

        equipo.setUpdatedAt(LocalDateTime.now());
        equipo = equipoRepository.save(equipo);
        return convertToDTO(equipo);
    }

    public void deleteEquipo(UUID id) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);
        if (equipo == null) {
            throw new NotFoundException("Equipo", id.toString());
        }
        equipoRepository.delete(equipo);
    }

    private EquipoDTO convertToDTO(Equipo equipo) {
        EquipoDTO equipoDTO = new EquipoDTO();
        equipoDTO.setId(equipo.getId());
        equipoDTO.setNombre(equipo.getNombre());
        equipoDTO.setProyectoId(equipo.getProyecto().getId());
        equipoDTO.setActivado(equipo.getActivado());
        return equipoDTO;
    }

    private Equipo convertToEntity(EquipoDTO equipoDTO) {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        Proyecto proyecto = proyectoRepository.findById(equipoDTO.getProyectoId()).orElse(null);
        if (proyecto == null) {
            throw new NotFoundException("Proyecto", equipoDTO.getProyectoId().toString());
        }
        equipo.setProyecto(proyecto);
        equipo.setActivado(equipoDTO.getActivado());
        return equipo;
    }

    /**
     * endpoints personalizados
     */

    //  public List<EquipoDTO> getEquiposConIntegrantesPorProyecto(UUID projectId) {
    //     List<Equipo> equipos = equipoRepository.findByProyectoId(projectId);
    
    //     return equipos.stream().map(equipo -> {
    //         EquipoDTO equipoDTO = DTOConverter.convertToEquipoDTO(equipo);
    
    //         // Obtener los integrantes del equipo a través de MiembroRepository
    //         List<UsuarioDTO> integrantes = miembroRepository.findByEquipoId(equipo.getId()).stream()
    //                 .map(miembro -> {
    //                     UsuarioDTO usuarioDTO = DTOConverter.convertToUsuarioDTO(miembro.getUsuario());
    //                     usuarioDTO.setRol(miembro.getRol().getNombre()); // Asigna el nombre del rol
    //                     return usuarioDTO;
    //                 })
    //                 .collect(Collectors.toList());
    
    //         equipoDTO.setIntegrantes(integrantes);
    //         return equipoDTO;
    //     }).collect(Collectors.toList());
    // }

    public List<EquipoDTO> getEquiposByProyecto(UUID projectId) {
        List<Equipo> equipos = equipoRepository.findEquiposByProyectoIdWithRoles(projectId);
    
        return equipos.stream().map(equipo -> {
            EquipoDTO equipoDTO = new EquipoDTO();
            equipoDTO.setId(equipo.getId());
            equipoDTO.setNombre(equipo.getNombre());
            equipoDTO.setProyectoId(equipo.getProyecto().getId());
            equipoDTO.setActivado(equipo.getActivado());
    
            // Mapear miembros a UsuarioDTO
            List<UsuarioDTO> integrantes = equipo.getMiembros().stream().map(miembro -> {
                Usuario usuario = miembro.getUsuario();
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setId(usuario.getId());
                usuarioDTO.setNombres(usuario.getNombres());
                usuarioDTO.setApellidos(usuario.getApellidos());
                usuarioDTO.setEmail(usuario.getEmail());
                usuarioDTO.setTelefono(usuario.getTelefono());
                usuarioDTO.setRol(miembro.getRol().getNombre());
                return usuarioDTO;
            }).collect(Collectors.toList());
    
            equipoDTO.setIntegrantes(integrantes);
            return equipoDTO;
        }).collect(Collectors.toList());
    }
    
    
    
    public List<EquipoDTO> getEquiposByProjectManager(UUID projectManagerId) {
        List<Equipo> equipos = equipoRepository.findEquiposByProjectManagerIdWithRoles(projectManagerId);

        return equipos.stream().map(equipo -> {
            EquipoDTO equipoDTO = new EquipoDTO();
            equipoDTO.setId(equipo.getId());
            equipoDTO.setNombre(equipo.getNombre());
            equipoDTO.setProyectoId(equipo.getProyecto().getId());
            equipoDTO.setActivado(equipo.getActivado());

            // Mapear miembros a UsuarioDTO
            List<UsuarioDTO> integrantes = equipo.getMiembros().stream().map(miembro -> {
                Usuario usuario = miembro.getUsuario();
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setId(usuario.getId());
                usuarioDTO.setNombres(usuario.getNombres());
                usuarioDTO.setApellidos(usuario.getApellidos());
                usuarioDTO.setEmail(usuario.getEmail());
                usuarioDTO.setTelefono(usuario.getTelefono());
                usuarioDTO.setRol(miembro.getRol().getNombre());
                return usuarioDTO;
            }).collect(Collectors.toList());

            equipoDTO.setIntegrantes(integrantes);
            return equipoDTO;
        }).collect(Collectors.toList());
    }




  
}
