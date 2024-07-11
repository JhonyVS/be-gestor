package com.umss.be_gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umss.be_gestor.model.ProductBacklog;

import java.util.UUID;

public interface ProductBacklogRepository extends JpaRepository<ProductBacklog, UUID> {
}
