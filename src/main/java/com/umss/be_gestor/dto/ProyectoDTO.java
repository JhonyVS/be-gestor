package com.umss.be_gestor.dto;

import java.util.Date;

public class ProyectoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_final;
    private Long idProjectManager;
    private Boolean activado = true;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Date getFecha_inicio() {
        return fecha_inicio;
    }
    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
    public Date getFecha_final() {
        return fecha_final;
    }
    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }
    public Long getIdProjectManager() {
        return idProjectManager;
    }
    public void setIdProjectManager(Long idProjectManager) {
        this.idProjectManager = idProjectManager;
    }
    public Boolean getActivado() {
        return activado;
    }
    public void setActivado(Boolean activado) {
        this.activado = activado;
    }

    // Getters and setters
}
