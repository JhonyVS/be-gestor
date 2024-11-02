package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Workspace;

import java.util.UUID;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    @EntityGraph(attributePaths = {"tableros", "tableros.tarjetas"})
    Optional<Workspace> findByProjectManager_Id(UUID projectManagerId);

}
