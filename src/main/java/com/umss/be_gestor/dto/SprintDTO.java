package com.umss.be_gestor.dto;

import java.util.UUID;

public class SprintDTO {

    private UUID id;
    private UUID proyectoId;
    private Integer numeroSprint;
    private Boolean activado;

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(UUID proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Integer getNumeroSprint() {
        return numeroSprint;
    }

    public void setNumeroSprint(Integer numeroSprint) {
        this.numeroSprint = numeroSprint;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
