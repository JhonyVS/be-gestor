package com.umss.be_gestor.security;

//Clase S4
public record JwtResponse(
    String jwt,
    String id,
    String username,
    String nombres,
    String apellidos
    
    ) {
}
