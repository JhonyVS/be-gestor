package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.ProductBacklogDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.ProductBacklog;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.repository.ProductBacklogRepository;
import com.umss.be_gestor.repository.ProyectoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductBacklogService {

    @Autowired
    private ProductBacklogRepository productBacklogRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<ProductBacklogDTO> getAllProductBacklogs() {
        return productBacklogRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductBacklog> getFullProductBacklogs() {
        return productBacklogRepository.findAll();
    }

    public ProductBacklogDTO getProductBacklogById(UUID id) {
        ProductBacklog productBacklog = productBacklogRepository.findById(id).orElse(null);
        if (productBacklog == null) {
            throw new NotFoundException("ProductBacklog", id.toString());
        }
        return convertToDTO(productBacklog);
    }

    public ProductBacklogDTO createProductBacklog(ProductBacklogDTO productBacklogDTO) {
        ProductBacklog productBacklog = convertToEntity(productBacklogDTO);
        productBacklog.setCreatedAt(LocalDateTime.now());
        productBacklog.setUpdatedAt(LocalDateTime.now());
        productBacklog.setActivado(true); // Inicializar activado como true
        productBacklog = productBacklogRepository.save(productBacklog);
        return convertToDTO(productBacklog);
    }

    public ProductBacklogDTO updateProductBacklog(UUID id, ProductBacklogDTO productBacklogDTO) {
        ProductBacklog productBacklog = productBacklogRepository.findById(id).orElse(null);
        if (productBacklog == null) {
            throw new NotFoundException("ProductBacklog", id.toString());
        }

        // Actualización parcial de los campos
        if (productBacklogDTO.getProyectoId() != null) {
            Proyecto proyecto = proyectoRepository.findById(productBacklogDTO.getProyectoId()).orElse(null);
            if (proyecto == null) {
                throw new NotFoundException("Proyecto", productBacklogDTO.getProyectoId().toString());
            }
            productBacklog.setProyecto(proyecto);
        }
        if (productBacklogDTO.getDescripcion() != null) {
            productBacklog.setDescripcion(productBacklogDTO.getDescripcion());
        }
        if (productBacklogDTO.getActivado() != null) {
            productBacklog.setActivado(productBacklogDTO.getActivado());
        }

        productBacklog.setUpdatedAt(LocalDateTime.now());
        productBacklog = productBacklogRepository.save(productBacklog);
        return convertToDTO(productBacklog);
    }

    public void deleteProductBacklog(UUID id) {
        ProductBacklog productBacklog = productBacklogRepository.findById(id).orElse(null);
        if (productBacklog == null) {
            throw new NotFoundException("ProductBacklog", id.toString());
        }
        productBacklogRepository.delete(productBacklog);
    }

    private ProductBacklogDTO convertToDTO(ProductBacklog productBacklog) {
        ProductBacklogDTO productBacklogDTO = new ProductBacklogDTO();
        productBacklogDTO.setId(productBacklog.getId());
        productBacklogDTO.setProyectoId(productBacklog.getProyecto().getId());
        productBacklogDTO.setDescripcion(productBacklog.getDescripcion());
        productBacklogDTO.setActivado(productBacklog.getActivado());
        return productBacklogDTO;
    }

    private ProductBacklog convertToEntity(ProductBacklogDTO productBacklogDTO) {
        ProductBacklog productBacklog = new ProductBacklog();
        Proyecto proyecto = proyectoRepository.findById(productBacklogDTO.getProyectoId()).orElse(null);
        if (proyecto == null) {
            throw new NotFoundException("Proyecto", productBacklogDTO.getProyectoId().toString());
        }
        productBacklog.setProyecto(proyecto);
        productBacklog.setDescripcion(productBacklogDTO.getDescripcion());
        productBacklog.setActivado(productBacklogDTO.getActivado());
        return productBacklog;
    }
}
