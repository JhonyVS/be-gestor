package com.umss.be_gestor.controller;

import com.umss.be_gestor.dto.EstadoTareaDTO;
import com.umss.be_gestor.model.EstadoTarea;
import com.umss.be_gestor.service.EstadoTareaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estado-tarea")
public class EstadoTareaController {

    private final EstadoTareaService estadoTareaService;

    public EstadoTareaController(EstadoTareaService estadoTareaService) {
        this.estadoTareaService = estadoTareaService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoTareaDTO>> getAllEstados() {
        List<EstadoTarea> estados = estadoTareaService.getAllEstados();
        List<EstadoTareaDTO> dtos = estados.stream()
            .map(estado -> new EstadoTareaDTO(estado.getId(), estado.getNombre()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EstadoTarea> getEstadoByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(estadoTareaService.getEstadoByNombre(nombre));
    }

    @PostMapping("/create")
    public ResponseEntity<EstadoTareaDTO> createEstado(@RequestBody EstadoTareaDTO estadoDTO) {
        EstadoTarea estado = estadoTareaService.saveEstado(estadoDTO);
        return ResponseEntity.ok(new EstadoTareaDTO(estado.getId(), estado.getNombre()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Integer id) {
        estadoTareaService.deleteEstado(id);
        return ResponseEntity.noContent().build();
    }
}
