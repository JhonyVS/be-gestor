package com.umss.be_gestor.dto;

import java.util.UUID;

public class AsignarHistoriaDTO {

    private UUID sprintBacklogId;
    private UUID historiaId;
    private Boolean activado;

    // Getters y setters
    public UUID getSprintBacklogId() {
        return sprintBacklogId;
    }

    public void setSprintBacklogId(UUID sprintBacklogId) {
        this.sprintBacklogId = sprintBacklogId;
    }

    public UUID getHistoriaId() {
        return historiaId;
    }

    public void setHistoriaId(UUID historiaId) {
        this.historiaId = historiaId;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
