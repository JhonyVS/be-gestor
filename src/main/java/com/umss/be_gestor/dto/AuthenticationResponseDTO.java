package com.umss.be_gestor.dto;


import java.util.UUID;

public class AuthenticationResponseDTO {
    private String jwt;
    private UUID id;
    private String username;
    private String nombres;
    private String apellidos;

    public AuthenticationResponseDTO(String jwt, UUID id,String username, String nombres, String apellidos) {
        this.jwt = jwt;
        this.id =id;
        this.username = username;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    public AuthenticationResponseDTO(){

    }
    // Getters y setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

}
