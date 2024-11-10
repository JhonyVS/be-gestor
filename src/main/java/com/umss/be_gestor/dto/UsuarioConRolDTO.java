package com.umss.be_gestor.dto;

import java.time.LocalDate;
import java.util.UUID;

public class UsuarioConRolDTO {
    private UUID id;
    private String nombres;
    private String apellidos;
    private String email; 
    private LocalDate nacimiento;
    private String rol; // Campo para el rol

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getRol() {
        return rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

}
