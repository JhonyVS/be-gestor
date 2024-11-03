package com.umss.be_gestor.dto;

import java.util.List;
import java.util.UUID;

public class WorkspaceResponseDTO {
    private UUID id;
    private UUID projectManagerId;
    private List<TableroDTO> tableros;

    public WorkspaceResponseDTO() {
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

    public List<TableroDTO> getTableros() {
        return tableros;
    }

    public void setTableros(List<TableroDTO> tableros) {
        this.tableros = tableros;
    }
}
