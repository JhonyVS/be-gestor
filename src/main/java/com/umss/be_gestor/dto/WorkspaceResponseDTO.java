package com.umss.be_gestor.dto;

import java.util.List;
import java.util.UUID;

public class WorkspaceResponseDTO {
    private UUID id;
    private UUID projectManagerId;

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

}
