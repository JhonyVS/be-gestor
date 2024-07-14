package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Sprint;

import java.util.UUID;

public interface SprintRepository extends JpaRepository<Sprint, UUID> {
}
