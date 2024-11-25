package com.umss.be_gestor.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.umss.be_gestor.dto.UsuarioBasicoDTO;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }
    @GetMapping("/full")
    public ResponseEntity<List<Usuario>> getFullUsuarios() {
        return ResponseEntity.ok(usuarioService.getFullUsuarios());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UsuarioDTO userDTO = usuarioService.getUserDTOByUsername(username);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailAvailability(@RequestParam String email) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("emailAvailable", usuarioService.isEmailAvailable(email));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(@RequestParam String username) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("usernameAvailable", usuarioService.isUsernameAvailable(username));
        return ResponseEntity.ok(response);
    }
    
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/create")
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(201).body(usuarioService.createUsuario(usuarioDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable UUID id, @RequestBody UsuarioDTO usuarioDTO) throws NotFoundException {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, usuarioDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) throws NotFoundException {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/basic")
    public ResponseEntity<UsuarioBasicoDTO> obtenerDatosBasicos(@PathVariable UUID id) {
        UsuarioBasicoDTO usuarioBasicDTO = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuarioBasicDTO);
    }

}
