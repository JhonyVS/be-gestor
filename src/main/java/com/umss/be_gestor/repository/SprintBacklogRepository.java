package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.SprintBacklog;

import java.util.UUID;

public interface SprintBacklogRepository extends JpaRepository<SprintBacklog, UUID> {
}
