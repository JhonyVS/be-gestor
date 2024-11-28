package com.umss.be_gestor.model;
import javax.persistence.*;

@Entity
@Table(name = "estado_tarea")
public class EstadoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true) // Esto asegura que el nombre no sea nulo ni duplicado
    private String nombre;

    public EstadoTarea() {
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

