package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Equipo;

import java.util.List;
import java.util.UUID;

public interface EquipoRepository extends JpaRepository<Equipo, UUID> {


    @EntityGraph(attributePaths = {"integrantes"})
    List<Equipo> findByProyectoId(UUID projectId);
}
