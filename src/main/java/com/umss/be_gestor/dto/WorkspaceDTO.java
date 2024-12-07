package com.umss.be_gestor.dto;

import java.util.UUID;

public class WorkspaceDTO {

    private UUID id;
    private UUID projectManagerId;
    private Boolean activado;


    public WorkspaceDTO(UUID id, UUID projectManagerId, Boolean activado) {
        this.id = id;
        this.projectManagerId = projectManagerId;
        this.activado = activado;
    }

    public WorkspaceDTO() {
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(UUID projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
