package com.umss.be_gestor.dto;

public class EstadoTareaDTO {

    private Integer id; // El ID del estado
    private String nombre; // El nombre del estado (por ejemplo: Pendiente, Trabajando)

    // Constructor vacío
    public EstadoTareaDTO() {}

    // Constructor con todos los campos
    public EstadoTareaDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
