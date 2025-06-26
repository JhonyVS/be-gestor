package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.umss.be_gestor.model.Historia;
import com.umss.be_gestor.model.ProductBacklog;
import com.umss.be_gestor.model.Proyecto;

import java.util.List;
import java.util.UUID;



public interface ProductBacklogRepository extends JpaRepository<ProductBacklog, UUID> {

    ProductBacklog findByProyecto(Proyecto proyecto);

    @Query("SELECT h FROM Historia h WHERE h.productBacklog.proyecto.id = :uuidProyecto")
    List<Historia> findHistoriasByProyecto(@Param("uuidProyecto") UUID uuidProyecto);

}
