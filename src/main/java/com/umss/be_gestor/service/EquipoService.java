package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.EquipoDTO;
import com.umss.be_gestor.dto.UsuarioBasicoDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Equipo;
import com.umss.be_gestor.model.Miembro;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.EquipoRepository;
import com.umss.be_gestor.repository.MiembroRepository;
import com.umss.be_gestor.repository.ProyectoRepository;
import com.umss.be_gestor.repository.UsuarioRepository;
import com.umss.be_gestor.util.DTOConverter;

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
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MiembroRepository miembroRepository;





    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;

    }

    public List<EquipoDTO> getAllEquipos() {
        return equipoRepository.findAll().stream()
                .map(DTOConverter::convertToDTO)
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
        return DTOConverter.convertToDTO(equipo);
    }

    public EquipoDTO createEquipo(EquipoDTO equipoDTO) {
        Equipo equipo = DTOConverter.convertToEntity(equipoDTO,proyectoRepository,usuarioRepository);
        equipo.setCreatedAt(LocalDateTime.now());
        equipo.setUpdatedAt(LocalDateTime.now());
        equipo.setActivado(true); // Inicializar activado como true
        equipo = equipoRepository.save(equipo);
        return DTOConverter.convertToDTO(equipo);
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
        return DTOConverter.convertToDTO(equipo);
    }

    public void deleteEquipo(UUID id) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);
        if (equipo == null) {
            throw new NotFoundException("Equipo", id.toString());
        }
        equipoRepository.delete(equipo);
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
            List<UsuarioBasicoDTO> integrantes = equipo.getMiembros().stream().map(miembro -> {
                Usuario usuario = miembro.getUsuario();
                UsuarioBasicoDTO usuarioBasicoDTO = new UsuarioBasicoDTO();
                usuarioBasicoDTO.setId(usuario.getId());
                usuarioBasicoDTO.setNombres(usuario.getNombres());
                usuarioBasicoDTO.setApellidos(usuario.getApellidos());
                usuarioBasicoDTO.setEmail(usuario.getEmail());
                usuarioBasicoDTO.setTelefono(usuario.getTelefono());
                usuarioBasicoDTO.setRol(miembro.getRol().getNombre());
                return usuarioBasicoDTO;
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
            if(equipoDTO.getProyectoId() != null)
                equipoDTO.setProyectoId(equipo.getProyecto().getId());
            if(equipoDTO.getUsuarioCapitan() != null)
                equipoDTO.setUsuarioCapitan(DTOConverter.convertirAUsuarioBasicoDTO(equipo.getCapitan()));
            equipoDTO.setActivado(equipo.getActivado());

            // Mapear miembros a UsuarioDTO
            List<UsuarioBasicoDTO> integrantes = equipo.getMiembros().stream().map(miembro -> {
                Usuario usuario = miembro.getUsuario();
                UsuarioBasicoDTO usuarioBasicoDTO = new UsuarioBasicoDTO();
                usuarioBasicoDTO.setId(usuario.getId());
                usuarioBasicoDTO.setNombres(usuario.getNombres());
                usuarioBasicoDTO.setApellidos(usuario.getApellidos());
                usuarioBasicoDTO.setEmail(usuario.getEmail());
                usuarioBasicoDTO.setTelefono(usuario.getTelefono());
                usuarioBasicoDTO.setRol(miembro.getRol().getNombre());
                return usuarioBasicoDTO;
            }).collect(Collectors.toList());

            equipoDTO.setIntegrantes(integrantes);
            return equipoDTO;
        }).collect(Collectors.toList());
    }

    public List<EquipoDTO> getEquiposByCapitan(UUID capitanId) {
        List<Equipo> equipos = equipoRepository.findEquiposByCapitanIdWithRoles(capitanId);
    
        return equipos.stream().map(equipo -> {
            EquipoDTO equipoDTO = new EquipoDTO();
            equipoDTO.setId(equipo.getId());
            equipoDTO.setNombre(equipo.getNombre());
            if (equipo.getProyecto() != null)
                equipoDTO.setProyectoId(equipo.getProyecto().getId());
            if (equipo.getCapitan() != null){
                UsuarioBasicoDTO capitan = DTOConverter.convertirAUsuarioBasicoDTO(equipo.getCapitan());
                capitan.setRol("Project Manager");
                equipoDTO.setUsuarioCapitan(capitan);
            }
            equipoDTO.setActivado(equipo.getActivado());
    
            // Mapear miembros a UsuarioDTO
            List<UsuarioBasicoDTO> integrantes = equipo.getMiembros().stream().map(miembro -> {
                UsuarioBasicoDTO usuarioBasicoDTO = DTOConverter.convertirAUsuarioBasicoDTO(miembro.getUsuario());
                usuarioBasicoDTO.setRol(miembro.getRol().getNombre());
                return usuarioBasicoDTO;
            }).collect(Collectors.toList());
    
            equipoDTO.setIntegrantes(integrantes);
            return equipoDTO;
        }).collect(Collectors.toList());
    }


    public List<EquipoDTO> getEquiposByUsuarioMiembro(UUID usuarioId) {
        List<Equipo> equipos = equipoRepository.findEquiposByUsuarioMiembroId(usuarioId);

        return equipos.stream().map(equipo -> {
            List<Miembro> miembros = miembroRepository.findMiembrosByEquipoId(equipo.getId());

            EquipoDTO equipoDTO = new EquipoDTO();
            equipoDTO.setId(equipo.getId());
            equipoDTO.setNombre(equipo.getNombre());
            equipoDTO.setActivado(equipo.getActivado());

            // Mapear miembros
            List<UsuarioBasicoDTO> integrantes = miembros.stream().map(miembro -> {
                UsuarioBasicoDTO usuarioBasicoDTO = DTOConverter.convertirAUsuarioBasicoDTO(miembro.getUsuario());
                usuarioBasicoDTO.setRol(miembro.getRol().getNombre());
                return usuarioBasicoDTO;
            }).collect(Collectors.toList());

            equipoDTO.setIntegrantes(integrantes);
            return equipoDTO;
        }).collect(Collectors.toList());
    }






  
}
