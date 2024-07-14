package com.umss.be_gestor.dto;

import java.util.UUID;

public class HistoriaDTO {

    private UUID id;
    private UUID productBacklogId;
    private UUID prioridadId;
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

    public UUID getProductBacklogId() {
        return productBacklogId;
    }

    public void setProductBacklogId(UUID productBacklogId) {
        this.productBacklogId = productBacklogId;
    }

    public UUID getPrioridadId() {
        return prioridadId;
    }

    public void setPrioridadId(UUID prioridadId) {
        this.prioridadId = prioridadId;
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
