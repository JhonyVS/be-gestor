package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.umss.be_gestor.model.Tarea;

import java.util.List;
import java.util.UUID;

public interface TareaRepository extends JpaRepository<Tarea, UUID> {

    /** Cuenta la cantidad de tipos de tareas que existen */
    @Query("SELECT t FROM Tarea t " +
           "JOIN t.tarjeta tarjeta " +
           "JOIN tarjeta.tablero tablero " +
           "WHERE tablero.proyecto.id = :proyectoId AND t.activado = true")
    List<Tarea> findAllByProyectoId(UUID proyectoId);
}
