package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Prioridad;

import java.util.UUID;

public interface PrioridadRepository extends JpaRepository<Prioridad, UUID> {
}
