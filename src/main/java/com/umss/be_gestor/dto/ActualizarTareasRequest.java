package com.umss.be_gestor.dto;
import java.util.List;
import java.util.UUID;

public class ActualizarTareasRequest {
    private UUID tarjetaId;
    private List<UUID> tareasIds;
    public ActualizarTareasRequest() {
    }
    public UUID getTarjetaId() {
        return tarjetaId;
    }
    public void setTarjetaId(UUID tarjetaId) {
        this.tarjetaId = tarjetaId;
    }
    public List<UUID> getTareasIds() {
        return tareasIds;
    }
    public void setTareasIds(List<UUID> tareasIds) {
        this.tareasIds = tareasIds;
    }

    // Getters y Setters
}
