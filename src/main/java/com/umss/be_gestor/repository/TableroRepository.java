package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.umss.be_gestor.model.Tablero;

import java.util.UUID;
import java.util.List;

public interface TableroRepository extends JpaRepository<Tablero, UUID> {

    // Método para encontrar tableros con un workspace asignado
    List<Tablero> findByWorkspaceIdIsNotNull();

    @Query("SELECT t FROM Tablero t WHERE t.proyecto IS NOT NULL")
    List<Tablero> findTablerosWithProyecto();

    // @EntityGraph(attributePaths = {"tarjetas"})
    // @Query("SELECT t FROM Tablero t WHERE t.workspace IS NOT NULL")
    // List<Tablero> findTablerosWithWorkspaceAndTarjetas();
    List<Tablero> findByWorkspaceId(UUID workspaceId);

    @EntityGraph(attributePaths = {"tarjetas"})
    @Query("SELECT t FROM Tablero t WHERE t.workspace IS NOT NULL")
    List<Tablero> findTablerosWithWorkspaceAndTarjetas();

}
