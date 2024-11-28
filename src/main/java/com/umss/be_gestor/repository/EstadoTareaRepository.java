package com.umss.be_gestor.repository;

import com.umss.be_gestor.model.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Integer> {
    Optional<EstadoTarea> findByNombre(String nombre);
}
