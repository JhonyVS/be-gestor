package com.umss.be_gestor.controller;

import com.umss.be_gestor.model.UsuarioModel;
import com.umss.be_gestor.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<UsuarioModel> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public UsuarioModel createUsuario(@RequestBody UsuarioModel usuario) {
        return usuarioService.createUsuario(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioModel updateUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuario) {
        return usuarioService.updateUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }
}
