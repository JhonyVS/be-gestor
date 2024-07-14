package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.RolDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Rol;
import com.umss.be_gestor.repository.RolRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<RolDTO> getAllRoles() {
        return rolRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Rol> getFullRoles() {
        return rolRepository.findAll();
    }

    public RolDTO getRolById(UUID id) {
        Rol rol = rolRepository.findById(id).orElse(null);
        if (rol == null) {
            throw new NotFoundException("Rol", id.toString());
        }
        return convertToDTO(rol);
    }

    public RolDTO createRol(RolDTO rolDTO) {
        Rol rol = convertToEntity(rolDTO);
        rol.setCreatedAt(LocalDateTime.now());
        rol.setUpdatedAt(LocalDateTime.now());
        rol.setActivado(true); // Inicializar activado como true
        rol = rolRepository.save(rol);
        return convertToDTO(rol);
    }

    public RolDTO updateRol(UUID id, RolDTO rolDTO) {
        Rol rol = rolRepository.findById(id).orElse(null);
        if (rol == null) {
            throw new NotFoundException("Rol", id.toString());
        }

        // Actualización parcial de los campos
        if (rolDTO.getNombre() != null) {
            rol.setNombre(rolDTO.getNombre());
        }
        if (rolDTO.getDescripcion() != null) {
            rol.setDescripcion(rolDTO.getDescripcion());
        }
        if (rolDTO.getActivado() != null) {
            rol.setActivado(rolDTO.getActivado());
        }

        rol.setUpdatedAt(LocalDateTime.now());
        rol = rolRepository.save(rol);
        return convertToDTO(rol);
    }

    public void deleteRol(UUID id) {
        Rol rol = rolRepository.findById(id).orElse(null);
        if (rol == null) {
            throw new NotFoundException("Rol", id.toString());
        }
        rolRepository.delete(rol);
    }

    private RolDTO convertToDTO(Rol rol) {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rol.getId());
        rolDTO.setNombre(rol.getNombre());
        rolDTO.setDescripcion(rol.getDescripcion());
        rolDTO.setActivado(rol.getActivado());
        return rolDTO;
    }

    private Rol convertToEntity(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setNombre(rolDTO.getNombre());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setActivado(rolDTO.getActivado());
        return rol;
    }
}
