package com.umss.be_gestor.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ComentarioDTO {

    private UUID id;
    private UsuarioBasicoDTO usuario;
    private LocalDateTime createdAt;
    private UUID idProyecto;
    private String contenido;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UsuarioBasicoDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBasicoDTO usuario) {
        this.usuario = usuario;
    }

    // Constructor vacío
    public ComentarioDTO() {}

    // Constructor completo
    public ComentarioDTO(UUID id,UsuarioBasicoDTO usuario, UUID idProyecto, String contenido) {
        this.id=id;
        this.idProyecto = idProyecto;
        this.usuario = usuario;
        this.contenido = contenido;
        this.createdAt = LocalDateTime.now();
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(UUID idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
