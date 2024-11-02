package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.umss.be_gestor.model.Tarjeta;

import java.util.UUID;
import java.util.List;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID> {

    @Query("SELECT tj FROM Tarjeta tj WHERE tj.tablero.workspace IS NOT NULL")
    List<Tarjeta> findTarjetasWithTableroWorkspace();

    // Método para encontrar tarjetas que pertenecen a tableros
    List<Tarjeta> findByTableroIdIsNotNull();
}
