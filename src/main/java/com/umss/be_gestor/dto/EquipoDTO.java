package com.umss.be_gestor.dto;

import java.util.List;
import java.util.UUID;

public class EquipoDTO {

    private UUID id;
    private String nombre;
    private UUID proyectoId;
    private UsuarioBasicoDTO usuarioCapitan;
    private Boolean activado;

    private List<UsuarioBasicoDTO> integrantes;

    

    public List<UsuarioBasicoDTO> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<UsuarioBasicoDTO> integrantes) {
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

    public UsuarioBasicoDTO getUsuarioCapitan() {
        return usuarioCapitan;
    }

    public void setUsuarioCapitan(UsuarioBasicoDTO usuarioCapitan) {
        this.usuarioCapitan = usuarioCapitan;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
