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

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioModel> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public ApiResponse<UsuarioModel> crearUsuario(UsuarioDTO usuarioDto) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setNacimiento(usuarioDto.getNacimiento());
        usuarioModel.setNombres(usuarioDto.getNombres());
        usuarioModel.setApellidos(usuarioDto.getApellidos());
        usuarioModel.setEmail(usuarioDto.getEmail());
        usuarioModel.setUsername(usuarioDto.getUsername());
        usuarioModel.setPassword(usuarioDto.getPassword());
        usuarioModel.setTelefono(usuarioDto.getTelefono());
        LocalDateTime now = LocalDateTime.now();
        usuarioModel.setCreatedAt(now);
        usuarioModel.setUpdatedAt(now);
    
        try {
            UsuarioModel usuarioGuardado = usuarioRepository.save(usuarioModel);
            return new ApiResponse<>(true, "Usuario registrado correctamente", usuarioGuardado);
        } catch (Exception e) {
            logger.error("Error al guardar usuario: " + e.getMessage());
            return new ApiResponse<>(false, "Error al guardar usuario: " + e.getMessage(), null);
        }
    }
    
    

    public UsuarioModel updateUsuario(Long id, UsuarioModel usuario) {
        // Verifica si el usuario existe antes de actualizarlo
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id); // asegura que el ID se mantenga consistente
            return usuarioRepository.save(usuario);
        }
        return null; // o lanza una excepción según tu lógica de negocio
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

}
