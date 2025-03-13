package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.umss.be_gestor.model.Usuario;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface IUsuarioRepository extends IGenericRepository<Usuario, UUID> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    // Consulta corregida para obtener los UUIDs de los proyectos donde el usuario es project manager
    @Query("SELECT p.id FROM Proyecto p WHERE p.projectManager.id = :userId")
    List<UUID> findProjectUUIDsByUserId(UUID userId);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

}

