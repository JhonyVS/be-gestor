package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Equipo;

import java.util.UUID;

public interface EquipoRepository extends JpaRepository<Equipo, UUID> {
}
