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

    @Query("SELECT DISTINCT e FROM Equipo e " +
        "JOIN FETCH e.miembros m " +
        "JOIN FETCH m.rol r " +
        "WHERE e.proyecto.id = :projectId")
    List<Equipo> findEquiposByProyectoIdWithRoles(@Param("projectId") UUID projectId);



    @Query("SELECT DISTINCT e FROM Equipo e " +
        "JOIN e.proyecto p " +
        "JOIN FETCH e.miembros m " +
        "JOIN FETCH m.rol r " +
        "WHERE p.projectManager.id = :projectManagerId")
    List<Equipo> findEquiposByProjectManagerIdWithRoles(@Param("projectManagerId") UUID projectManagerId);


    @Query("SELECT DISTINCT e FROM Equipo e " +
       "LEFT JOIN FETCH e.miembros m " +
       "LEFT JOIN FETCH m.usuario u " +
       "LEFT JOIN FETCH m.rol r " +
       "WHERE e.capitan.id = :capitanId")
    List<Equipo> findEquiposByCapitanIdWithRoles(@Param("capitanId") UUID capitanId);

    @Query("SELECT DISTINCT e FROM Equipo e " +
        "JOIN FETCH e.miembros m " +
        "JOIN FETCH m.usuario u " +
        "WHERE u.id = :usuarioId")
    List<Equipo> findEquiposByUsuarioMiembroId(@Param("usuarioId") UUID usuarioId);







}
