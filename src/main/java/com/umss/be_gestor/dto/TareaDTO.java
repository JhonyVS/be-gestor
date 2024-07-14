package com.umss.be_gestor.dto;

import java.util.UUID;

public class TareaDTO {

    private UUID id;
    private UUID historiaId;
    private UUID tarjetaId;
    private UUID usuarioAsignadoId;
    private String titulo;
    private String descripcion;
    private Integer estimacion;
    private Boolean activado;

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHistoriaId() {
        return historiaId;
    }

    public void setHistoriaId(UUID historiaId) {
        this.historiaId = historiaId;
    }

    public UUID getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(UUID tarjetaId) {
        this.tarjetaId = tarjetaId;
    }

    public UUID getUsuarioAsignadoId() {
        return usuarioAsignadoId;
    }

    public void setUsuarioAsignadoId(UUID usuarioAsignadoId) {
        this.usuarioAsignadoId = usuarioAsignadoId;
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

    public Integer getEstimacion() {
        return estimacion;
    }

    public void setEstimacion(Integer estimacion) {
        this.estimacion = estimacion;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
