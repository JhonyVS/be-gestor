package com.umss.be_gestor.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.umss.be_gestor.record.CustomErrorResponse;
import com.umss.be_gestor.record.GenericResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.springframework.security.core.AuthenticationException;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<GenericResponse<CustomErrorResponse>> handleGenericException(Exception e, WebRequest web) {
    //     Throwable rootCause = e;
    //     while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
    //         rootCause = rootCause.getCause();
    //     }

    //     logger.error("Excepción capturada: {}", e.getClass().getName());
    //     logger.error("Causa raíz de la excepción: {}", rootCause.getClass().getName());

    //     CustomErrorResponse response = new CustomErrorResponse(
    //         HttpStatus.INTERNAL_SERVER_ERROR.value(), 
    //         e.getMessage(), 
    //         web.getDescription(false)
    //     );

    //     return new ResponseEntity<>(
    //         new GenericResponse<>(
    //             LocalDateTime.now(),
    //             HttpStatus.INTERNAL_SERVER_ERROR.toString(),
    //             List.of(response)
    //         ), 
    //         HttpStatus.INTERNAL_SERVER_ERROR
    //     );
    // }


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
    public ResponseEntity<GenericResponse<CustomErrorResponse>> handleResponseStatusException(ResponseStatusException e, WebRequest web) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(404,e.getMessage(),web.getDescription(false));
        return new ResponseEntity<>(new GenericResponse<>(LocalDateTime.now(),"error", List.of(errorResponse)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericResponse<CustomErrorResponse>> handleBadCredentialsException(BadCredentialsException ex, WebRequest web) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(401,ex.getMessage(),web.getDescription(false));
        return new ResponseEntity<>(new GenericResponse<>(LocalDateTime.now(),"Usuario o contraseña incorrectos", List.of(errorResponse)), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<GenericResponse<CustomErrorResponse>> handleCustomAuthenticationException(CustomAuthenticationException e, WebRequest web) {
        CustomErrorResponse response = new CustomErrorResponse(
            HttpStatus.UNAUTHORIZED.value(), 
            e.getMessage(), 
            web.getDescription(false)
        );

        return new ResponseEntity<>(
            new GenericResponse<>(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.toString(),
                List.of(response)
            ), 
            HttpStatus.UNAUTHORIZED
        );
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", e.getMessage());
        errorResponse.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    


}