package com.umss.be_gestor.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.ProductBacklogDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.ProductBacklog;
import com.umss.be_gestor.service.ProductBacklogService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product_backlog")
public class ProductBacklogController {

    @Autowired
    private ProductBacklogService productBacklogService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductBacklogDTO>> getAllProductBacklogs() {
        return ResponseEntity.ok(productBacklogService.getAllProductBacklogs());
    }

    @GetMapping("/full")
    public ResponseEntity<List<ProductBacklog>> getFullProductBacklogs() {
        return ResponseEntity.ok(productBacklogService.getFullProductBacklogs());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductBacklogDTO> getProductBacklogById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(productBacklogService.getProductBacklogById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductBacklogDTO> createProductBacklog(@RequestBody ProductBacklogDTO productBacklogDTO) {
        return ResponseEntity.status(201).body(productBacklogService.createProductBacklog(productBacklogDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductBacklogDTO> updateProductBacklog(@PathVariable UUID id, @RequestBody ProductBacklogDTO productBacklogDTO) throws NotFoundException {
        return ResponseEntity.ok(productBacklogService.updateProductBacklog(id, productBacklogDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductBacklog(@PathVariable UUID id) throws NotFoundException {
        productBacklogService.deleteProductBacklog(id);
        return ResponseEntity.noContent().build();
    }
}
