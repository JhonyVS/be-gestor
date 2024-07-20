package com.umss.be_gestor.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("error", "Conflict");
        errorResponse.put("message", "El miembro ya está registrado en el equipo con ese rol");
        errorResponse.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", e.getStatus().value());
        errorResponse.put("error", e.getStatus().getReasonPhrase());
        errorResponse.put("message", e.getReason());
        errorResponse.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", "Nombre de usuario o contraseña incorrectos");
        errorResponse.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", "Nombre de usuario no encontrado");
        errorResponse.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}