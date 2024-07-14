package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Historia;

import java.util.UUID;

public interface HistoriaRepository extends JpaRepository<Historia, UUID> {
}
