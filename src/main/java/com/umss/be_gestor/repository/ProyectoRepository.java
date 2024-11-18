package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.umss.be_gestor.model.Proyecto;

import java.util.List;
import java.util.UUID;

public interface ProyectoRepository extends JpaRepository<Proyecto, UUID> {

    List<Proyecto> findByProjectManagerId(UUID projectManagerId);

    @Query("""
        SELECT p
        FROM Proyecto p
        WHERE p.projectManager.id = :projectManagerId
    """)
    List<Proyecto> findAllByProjectManagerId(@Param("projectManagerId") UUID projectManagerId);

}
