package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Rol;

import java.util.UUID;

public interface RolRepository extends JpaRepository<Rol, UUID> {
}
