package com.umss.be_gestor.repository;

import com.umss.be_gestor.model.ProyectoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<ProyectoModel, Long> {
}
