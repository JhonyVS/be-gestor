package com.umss.be_gestor.dto;

import java.util.List;
import java.util.UUID;

public class TableroDTO {

    private UUID id;
    private UUID proyectoId;
    private UUID workspaceId;
    private String titulo;
    private String descripcion;
    private Boolean activado;
    private List<TarjetaDTO> tarjetas; // Agregar la lista de tarjetas

    public TableroDTO() {
    }


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

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(UUID workspaceId) {
        this.workspaceId = workspaceId;
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

    public List<TarjetaDTO> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetaDTO> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
