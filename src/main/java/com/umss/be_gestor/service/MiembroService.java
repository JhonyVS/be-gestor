package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.umss.be_gestor.dto.MiembroDTO;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Miembro;
import com.umss.be_gestor.model.MiembroId;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.model.Equipo;
import com.umss.be_gestor.model.Rol;
import com.umss.be_gestor.repository.MiembroRepository;
import com.umss.be_gestor.repository.UsuarioRepository;
import com.umss.be_gestor.util.DTOConverter;
import com.umss.be_gestor.repository.EquipoRepository;
import com.umss.be_gestor.repository.RolRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private RolRepository rolRepository;


    public List<MiembroDTO> getAllMiembros() {
        return miembroRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Miembro> getFullMiembros() {
        return miembroRepository.findAll();
    }


    public MiembroService(MiembroRepository miembroRepository, DTOConverter dtoConverter) {
        this.miembroRepository = miembroRepository;
    }

    public List<UsuarioDTO> getIntegrantesByEquipo(UUID equipoId) {
        return miembroRepository.findByEquipo_Id(equipoId).stream()
                .map(miembro -> DTOConverter.convertToUsuarioDTO(miembro.getUsuario()))
                .collect(Collectors.toList());
    }

    public MiembroDTO getMiembroById(UUID usuarioId, UUID equipoId, UUID rolId) {
        MiembroId miembroId = new MiembroId(usuarioId, equipoId, rolId);

        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro == null) {
            throw new NotFoundException("Miembro", usuarioId.toString() + ", " + equipoId.toString() + ", " + rolId.toString());
        }
        return convertToDTO(miembro);
    }
    

    public MiembroDTO createMiembro(MiembroDTO miembroDTO) {
    // Verificar si la combinación de usuario, equipo y rol ya existe
        MiembroId miembroId = new MiembroId(
            miembroDTO.getUsuarioId(),
            miembroDTO.getEquipoId(),
            miembroDTO.getRolId()
        );

        if (miembroRepository.existsById(miembroId)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "El miembro ya está registrado en el equipo con ese rol");
        }

        Miembro miembro = convertToEntity(miembroDTO);
        miembro.setCreatedAt(LocalDateTime.now());
        miembro.setUpdatedAt(LocalDateTime.now());
        miembro.setActivado(true); // Inicializar activado como true
        miembro = miembroRepository.save(miembro);
        return convertToDTO(miembro);
    }

    public MiembroDTO updateMiembro(UUID usuarioId, UUID equipoId, UUID rolId, MiembroDTO miembroDTO) {
        MiembroId miembroId = new MiembroId(usuarioId, equipoId, rolId);

        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro == null) {
            throw new NotFoundException("Miembro", usuarioId.toString() + ", " + equipoId.toString() + ", " + rolId.toString());
        }

        // Actualización parcial de los campos
        if (miembroDTO.getActivado() != null) {
            miembro.setActivado(miembroDTO.getActivado());
        }

        miembro.setUpdatedAt(LocalDateTime.now());
        miembro = miembroRepository.save(miembro);
        return convertToDTO(miembro);
    }

    public void deleteMiembro(UUID usuarioId, UUID equipoId, UUID rolId) {
        MiembroId miembroId = new MiembroId(usuarioId, equipoId, rolId);

        Miembro miembro = miembroRepository.findById(miembroId).orElse(null);
        if (miembro == null) {
            throw new NotFoundException("Miembro", usuarioId.toString() + ", " + equipoId.toString() + ", " + rolId.toString());
        }
        miembroRepository.delete(miembro);
    }

    private MiembroDTO convertToDTO(Miembro miembro) {
        MiembroDTO miembroDTO = new MiembroDTO();
        miembroDTO.setUsuarioId(miembro.getUsuario().getId());
        miembroDTO.setEquipoId(miembro.getEquipo().getId());
        miembroDTO.setRolId(miembro.getRol().getId());
        miembroDTO.setActivado(miembro.getActivado());
        return miembroDTO;
    }

    private Miembro convertToEntity(MiembroDTO miembroDTO) {
        Miembro miembro = new Miembro();

        Usuario usuario = usuarioRepository.findById(miembroDTO.getUsuarioId()).orElse(null);
        if (usuario == null) {
            throw new NotFoundException("Usuario", miembroDTO.getUsuarioId().toString());
        }
        miembro.setUsuario(usuario);

        Equipo equipo = equipoRepository.findById(miembroDTO.getEquipoId()).orElse(null);
        if (equipo == null) {
            throw new NotFoundException("Equipo", miembroDTO.getEquipoId().toString());
        }
        miembro.setEquipo(equipo);

        Rol rol = rolRepository.findById(miembroDTO.getRolId()).orElse(null);
        if (rol == null) {
            throw new NotFoundException("Rol", miembroDTO.getRolId().toString());
        }
        miembro.setRol(rol);

        miembro.setActivado(miembroDTO.getActivado());
        return miembro;
    }
}
