package com.umss.be_gestor.controller;

import com.umss.be_gestor.ApiResponse;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.model.UsuarioModel;
import com.umss.be_gestor.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {



    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/full")
	public ResponseEntity<ApiResponse<List<UsuarioModel>>> getAllUsers(){
		ApiResponse<List<UsuarioModel>> usuarios = usuarioService.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(usuarios);
	}

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> getAllUsuarios() {
        ApiResponse<List<UsuarioDTO>> response = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> getUsuarioById(@PathVariable Long id) {
        ApiResponse<UsuarioDTO> response = usuarioService.getUsuarioById(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrarUsuario(@RequestBody UsuarioDTO usuarioDto) {
        ApiResponse<UsuarioDTO> response = usuarioService.crearUsuario(usuarioDto);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDto) {
        ApiResponse<UsuarioDTO> response = usuarioService.updateUsuario(id, usuarioDto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> desactivarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDto) {
        ApiResponse<UsuarioDTO> response = usuarioService.desactivarUsuario(id, usuarioDto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }
}
