package com.umss.be_gestor.dto;

import java.util.UUID;

public class MiembroDTO {

    private UUID usuarioId;
    private UUID equipoId;
    private UUID rolId;
    private Boolean activado;

    // Getters y setters
    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(UUID equipoId) {
        this.equipoId = equipoId;
    }

    public UUID getRolId() {
        return rolId;
    }

    public void setRolId(UUID rolId) {
        this.rolId = rolId;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
