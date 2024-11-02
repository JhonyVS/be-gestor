package com.umss.be_gestor.dto;

import java.util.List;
import java.util.UUID;

public class TarjetaDTO {

    private UUID id;
    private UUID tableroId;
    private String titulo;
    private String descripcion;
    private Boolean activado;

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTableroId() {
        return tableroId;
    }

    public void setTableroId(UUID tableroId) {
        this.tableroId = tableroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }

}

