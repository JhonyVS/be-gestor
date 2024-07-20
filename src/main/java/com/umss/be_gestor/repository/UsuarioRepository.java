package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umss.be_gestor.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}

