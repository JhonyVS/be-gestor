package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Tarea;

import java.util.UUID;

public interface TareaRepository extends JpaRepository<Tarea, UUID> {
}
