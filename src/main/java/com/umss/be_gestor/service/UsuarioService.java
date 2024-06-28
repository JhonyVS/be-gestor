package com.umss.be_gestor.service;


import com.umss.be_gestor.model.UsuarioModel;
import com.umss.be_gestor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioModel> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModel createUsuario(UsuarioModel usuario) {
        // Aquí puedes realizar validaciones o lógica adicional antes de guardar
        return usuarioRepository.save(usuario);
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
