package com.umss.be_gestor.controller;

import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.IUsuarioRepository;
import com.umss.be_gestor.security.JwtRequest;
import com.umss.be_gestor.security.JwtResponse;
import com.umss.be_gestor.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final IUsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req) throws Exception{
        authenticate(req.getUsername(), req.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Obtener información adicional del usuario
        Usuario usuario = usuarioRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

                return ResponseEntity.ok(new JwtResponse(
                    token,
                    usuario.getId().toString(),
                    usuario.getUsername(),
                    usuario.getNombres(),
                    usuario.getApellidos()
            ));
    }


    private void authenticate(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
