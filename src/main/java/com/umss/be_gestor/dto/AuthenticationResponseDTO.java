package com.umss.be_gestor.dto;


import java.util.UUID;

public class AuthenticationResponseDTO {
    private String jwt;
    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    private String username;
    private String nombres;
    private String apellidos;
    //private List<UUID> projectUUIDs;

    public AuthenticationResponseDTO(String jwt, UUID id,String username, String nombres, String apellidos) {
        this.jwt = jwt;
        this.id =id;
        this.username = username;
        this.nombres = nombres;
        this.apellidos = apellidos;
        //this.projectUUIDs = projectUUIDs;
    }
    public AuthenticationResponseDTO(){

    }

    // Getters y setters
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

    // public List<UUID> getProjectUUIDs() {
    //     return projectUUIDs;
    // }

    // public void setProjectUUIDs(List<UUID> projectUUIDs) {
    //     this.projectUUIDs = projectUUIDs;
    // }
}
