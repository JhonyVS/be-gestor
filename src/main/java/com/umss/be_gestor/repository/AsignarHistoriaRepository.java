package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.umss.be_gestor.model.AsignarHistoria;
import com.umss.be_gestor.model.AsignarHistoriaId;

public interface AsignarHistoriaRepository extends JpaRepository<AsignarHistoria, AsignarHistoriaId> {
}
