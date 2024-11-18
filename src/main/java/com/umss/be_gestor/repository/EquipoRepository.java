package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.umss.be_gestor.model.Equipo;

import java.util.List;
import java.util.UUID;

public interface EquipoRepository extends JpaRepository<Equipo, UUID> {


    @EntityGraph(attributePaths = {"integrantes"})
    List<Equipo> findByProyectoId(UUID projectId);

    @Query("""
        SELECT e
        FROM Equipo e
        WHERE e.proyecto.id = :projectId
    """)
    List<Equipo> findAllByProyectoId(@Param("projectId") UUID projectId);


    @Query("""
        SELECT e
        FROM Equipo e
        LEFT JOIN FETCH e.miembros m
        LEFT JOIN FETCH m.usuario
        WHERE e.proyecto.projectManager.id = :userId
    """)
    List<Equipo> findAllByProjectManagerId(@Param("userId") UUID userId);

}
