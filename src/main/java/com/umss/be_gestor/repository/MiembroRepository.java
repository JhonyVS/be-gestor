package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.Miembro;
import com.umss.be_gestor.model.MiembroId;

public interface MiembroRepository extends JpaRepository<Miembro, MiembroId> {
}
