package com.umss.be_gestor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umss.be_gestor.record.CustomErrorResponse;
import com.umss.be_gestor.record.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper como campo

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String message = "Error de autenticación";

        // Si la causa es BadCredentialsException, mostramos el error personalizado
        if (authException instanceof BadCredentialsException || "Bad credentials".equals(authException.getMessage())) {
            message = "Usuario o contraseña incorrectos";
        }

        // Crear la respuesta de error personalizada
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            HttpStatus.UNAUTHORIZED.value(), 
            message, 
            request.getRequestURI()
        );

        GenericResponse<CustomErrorResponse> responseBody = new GenericResponse<>(
            LocalDateTime.now(),
            "Error de autenticación",
            List.of(errorResponse)
        );

        // Configurar la respuesta HTTP
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Escribir la respuesta JSON en el cuerpo de la respuesta
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}