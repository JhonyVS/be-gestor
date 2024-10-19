package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.umss.be_gestor.model.Usuario;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    // Consulta corregida para obtener los UUIDs de los proyectos donde el usuario es project manager
    @Query("SELECT p.id FROM Proyecto p WHERE p.projectManager.id = :userId")
    List<UUID> findProjectUUIDsByUserId(UUID userId);
}

