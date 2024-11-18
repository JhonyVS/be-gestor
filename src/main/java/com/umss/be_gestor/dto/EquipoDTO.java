package com.umss.be_gestor.dto;

import java.util.List;
import java.util.UUID;

public class EquipoDTO {

    private UUID id;
    private String nombre;
    private UUID proyectoId;
    private Boolean activado;

    private List<UsuarioDTO> integrantes;

    

    public List<UsuarioDTO> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<UsuarioDTO> integrantes) {
        this.integrantes = integrantes;
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UUID getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(UUID proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
