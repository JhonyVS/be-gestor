package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Tablero;

import java.util.UUID;

public interface TableroRepository extends JpaRepository<Tablero, UUID> {
}
