package com.umss.be_gestor.model;

import java.io.Serializable;
import java.util.UUID;
import java.util.Objects;

public class MiembroId implements Serializable {

    private UUID usuario;
    private UUID equipo;
    private UUID rol;

    public MiembroId() {
    }

    public MiembroId(UUID usuario, UUID equipo, UUID rol) {
        this.usuario = usuario;
        this.equipo = equipo;
        this.rol = rol;
    }

    // Getters, setters, hashCode y equals
    public UUID getUsuario() {
        return usuario;
    }

    public void setUsuario(UUID usuario) {
        this.usuario = usuario;
    }

    public UUID getEquipo() {
        return equipo;
    }

    public void setEquipo(UUID equipo) {
        this.equipo = equipo;
    }

    public UUID getRol() {
        return rol;
    }

    public void setRol(UUID rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiembroId miembroId = (MiembroId) o;
        return Objects.equals(usuario, miembroId.usuario) &&
               Objects.equals(equipo, miembroId.equipo) &&
               Objects.equals(rol, miembroId.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, equipo, rol);
    }
}
