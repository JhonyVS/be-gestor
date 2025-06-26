package com.umss.be_gestor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historia")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Historia {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_product_backlog", nullable = false)
    private ProductBacklog productBacklog;

    @ManyToOne
    @JoinColumn(name = "id_prioridad", nullable = false)
    private Prioridad prioridad;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "codigo")
    private String codigo;


    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estimacion")
    private Integer estimacion;

    @Column(name = "activado", nullable = false)
    private Boolean activado;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    
}
