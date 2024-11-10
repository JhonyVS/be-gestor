package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Proyecto;

import java.util.List;
import java.util.UUID;

public interface ProyectoRepository extends JpaRepository<Proyecto, UUID> {

    List<Proyecto> findByProjectManagerId(UUID projectManagerId);


}
