package com.umss.be_gestor.model;

import java.io.Serializable;
import java.util.UUID;
import java.util.Objects;

public class AsignarHistoriaId implements Serializable {

    private UUID sprintBacklog;
    private UUID historia;

    public AsignarHistoriaId() {
    }

    public AsignarHistoriaId(UUID sprintBacklog, UUID historia) {
        this.sprintBacklog = sprintBacklog;
        this.historia = historia;
    }

    // Getters, setters, hashCode y equals
    public UUID getSprintBacklog() {
        return sprintBacklog;
    }

    public void setSprintBacklog(UUID sprintBacklog) {
        this.sprintBacklog = sprintBacklog;
    }

    public UUID getHistoria() {
        return historia;
    }

    public void setHistoria(UUID historia) {
        this.historia = historia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsignarHistoriaId that = (AsignarHistoriaId) o;
        return Objects.equals(sprintBacklog, that.sprintBacklog) &&
               Objects.equals(historia, that.historia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sprintBacklog, historia);
    }
}
