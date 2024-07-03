package com.umss.be_gestor.service;


import com.umss.be_gestor.model.UsuarioModel;
import com.umss.be_gestor.ApiResponse;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ApiResponse<List<UsuarioModel>> getAllUsers() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        return new ApiResponse<>(true, "Lista de usuarios obtenida correctamente", usuarios);
    }

    public ApiResponse<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return new ApiResponse<>(true, "Lista de usuarios obtenida correctamente", usuariosDTO);
    }

    public ApiResponse<UsuarioDTO> getUsuarioById(Long id) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioDTO usuarioDTO = convertToDto(usuarioOptional.get());
            return new ApiResponse<>(true, "Usuario encontrado", usuarioDTO);
        } else {
            return new ApiResponse<>(false, "Usuario no encontrado", null);
        }
    }

    public ApiResponse<UsuarioDTO> crearUsuario(UsuarioDTO usuarioDto) {
        UsuarioModel usuarioModel = convertToEntity(usuarioDto);
    
        try {
            usuarioRepository.save(usuarioModel);
            return new ApiResponse<>(true, "Usuario registrado correctamente", usuarioDto);
        } catch (Exception e) {
            logger.error("Error al guardar usuario: " + e.getMessage());
            return new ApiResponse<>(false, "Error al guardar usuario: " + e.getMessage(), null);
        }
    }

    public ApiResponse<UsuarioDTO> updateUsuario(Long id, UsuarioDTO usuarioDto) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            UsuarioModel usuarioModel = usuarioOptional.get();
            
            // Solo actualiza los campos si se proporcionan en el DTO
            if (usuarioDto.getNombres() != null) {
                usuarioModel.setNombres(usuarioDto.getNombres());
            }
            if (usuarioDto.getApellidos() != null) {
                usuarioModel.setApellidos(usuarioDto.getApellidos());
            }
            if (usuarioDto.getNacimiento() != null) {
                usuarioModel.setNacimiento(usuarioDto.getNacimiento());
            }
            if (usuarioDto.getEmail() != null) {
                usuarioModel.setEmail(usuarioDto.getEmail());
            }
            if (usuarioDto.getTelefono() != null) {
                usuarioModel.setTelefono(usuarioDto.getTelefono());
            }
            if (usuarioDto.getUsername() != null) {
                usuarioModel.setUsername(usuarioDto.getUsername());
            }
            if (usuarioDto.getPassword() != null) {
                usuarioModel.setPassword(usuarioDto.getPassword());
            }
            if (usuarioDto.isActivado() != null) {
                usuarioModel.setActivado(usuarioDto.isActivado());
            }
            
            
            usuarioModel.setUpdatedAt(LocalDateTime.now());

            UsuarioModel updatedUsuario = usuarioRepository.save(usuarioModel);
            UsuarioDTO updatedUsuarioDTO = convertToDto(updatedUsuario);
            return new ApiResponse<>(true, "Usuario actualizado correctamente", updatedUsuarioDTO);
        } else {
            return new ApiResponse<>(false, "Usuario no encontrado", null);
        }
    }

    public ApiResponse<UsuarioDTO> desactivarUsuario(Long id, UsuarioDTO usuarioDto) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            UsuarioModel usuarioModel = usuarioOptional.get();
            
            // Solo actualiza los campos si se proporcionan en el DTO
            if (usuarioDto.isActivado() != null) {
                usuarioModel.setActivado(usuarioDto.isActivado());
            }
            if (usuarioDto.getMotivoSuspension() != null) {
                usuarioModel.setMotivoSuspension(usuarioDto.getMotivoSuspension());
            }
            
            usuarioModel.setUpdatedAt(LocalDateTime.now());

            UsuarioModel updatedUsuario = usuarioRepository.save(usuarioModel);
            UsuarioDTO updatedUsuarioDTO = convertToDto(updatedUsuario);
            return new ApiResponse<>(true, "Usuario actualizado correctamente, "+usuarioDto.getMotivoSuspension(), updatedUsuarioDTO);
        } else {
            return new ApiResponse<>(false, "Usuario no encontrado", null);
        }
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }


    public UsuarioDTO convertToDto(UsuarioModel usuarioModel) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuarioModel.getId());
        usuarioDTO.setNombres(usuarioModel.getNombres());
        usuarioDTO.setApellidos(usuarioModel.getApellidos());
        usuarioDTO.setNacimiento(usuarioModel.getNacimiento());
        usuarioDTO.setEmail(usuarioModel.getEmail());
        usuarioDTO.setTelefono(usuarioModel.getTelefono());
        usuarioDTO.setUsername(usuarioModel.getUsername());
        usuarioDTO.setPassword(usuarioModel.getPassword());
        usuarioDTO.setActivado(usuarioModel.isActivado());
        usuarioDTO.setMotivoSuspension(usuarioModel.getMotivoSuspension());
        //usuarioDTO.setCreatedAt(usuarioModel.getCreatedAt());
        //usuarioDTO.setUpdatedAt(usuarioModel.getUpdatedAt());
        return usuarioDTO;
    }

    public UsuarioModel convertToEntity(UsuarioDTO usuarioDTO) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(usuarioDTO.getId());
        usuarioModel.setNombres(usuarioDTO.getNombres());
        usuarioModel.setApellidos(usuarioDTO.getApellidos());
        usuarioModel.setNacimiento(usuarioDTO.getNacimiento());
        usuarioModel.setEmail(usuarioDTO.getEmail());
        usuarioModel.setTelefono(usuarioDTO.getTelefono());
        usuarioModel.setUsername(usuarioDTO.getUsername());
        usuarioModel.setPassword(usuarioDTO.getPassword());;
        usuarioModel.setActivado(usuarioDTO.isActivado());
        usuarioModel.setMotivoSuspension(usuarioDTO.getMotivoSuspension());
        //usuarioModel.setCreatedAt(usuarioDTO.getCreatedAt());
        //usuarioModel.setUpdatedAt(usuarioDTO.getUpdatedAt());
        return usuarioModel;
    }

}
