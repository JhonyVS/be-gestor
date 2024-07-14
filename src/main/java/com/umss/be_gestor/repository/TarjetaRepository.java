package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Tarjeta;

import java.util.UUID;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID> {
}
