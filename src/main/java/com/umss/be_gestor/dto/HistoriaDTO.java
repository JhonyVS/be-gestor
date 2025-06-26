package com.umss.be_gestor.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriaDTO {

    private UUID id;
    private UUID productBacklogId;
    private UUID prioridadId;
    private String titulo;
    private String descripcion;
    private Integer estimacion;
    private Boolean activado;
    private String codigo;


  
}
