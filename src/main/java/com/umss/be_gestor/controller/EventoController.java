package com.umss.be_gestor.controller;

import com.umss.be_gestor.dto.EventoDTO;
import com.umss.be_gestor.service.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/create")
    public ResponseEntity<EventoDTO> guardarEvento(@RequestBody EventoDTO eventoDTO) {
        EventoDTO guardado = eventoService.guardarEvento(eventoDTO);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<EventoDTO>> listarEventosPorProyecto(@PathVariable UUID idProyecto) {
        List<EventoDTO> eventos = eventoService.listarEventosPorProyecto(idProyecto);
        return ResponseEntity.ok(eventos);
    }
}
