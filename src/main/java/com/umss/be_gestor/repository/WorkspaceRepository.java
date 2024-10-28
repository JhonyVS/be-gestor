package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Workspace;

import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
}
