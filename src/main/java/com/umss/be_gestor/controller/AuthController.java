package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.umss.be_gestor.dto.AuthenticationResponseDTO;
import com.umss.be_gestor.model.AuthenticationRequest;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.service.UsuarioService;
import com.umss.be_gestor.util.JwtUtil;


@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        // Autenticar al usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        // Cargar detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Obtener el usuario desde la base de datos
        UsuarioDTO usuario = usuarioService.getUsuarioByUsername(authenticationRequest.getUsername());

        // Crear y devolver el DTO con el JWT, username, nombres, apellidos y proyectos
        AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(
                jwt,
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombres(),
                usuario.getApellidos()
        );

        return ResponseEntity.ok(responseDTO);
    }

}
