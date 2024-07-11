package com.umss.be_gestor.dto;

import java.time.LocalDate;
import java.util.UUID;

public class UsuarioDTO {
    
    private UUID id;
    private String nombres;
    private String apellidos;
    private LocalDate nacimiento;
    private String email;
    private String telefono;
    private String username;
    private String password;
    private Boolean activado;
    private String motivoSuspension;
    public UsuarioDTO() {
    }
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
    public LocalDate getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean isActivado() {
        return activado;
    }
    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
    public String getMotivoSuspension() {
        return motivoSuspension;
    }
    public void setMotivoSuspension(String motivoSuspension) {
        this.motivoSuspension = motivoSuspension;
    }

    // Getters y setters
}
