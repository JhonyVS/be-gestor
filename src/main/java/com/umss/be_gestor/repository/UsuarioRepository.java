package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umss.be_gestor.model.Usuario;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
