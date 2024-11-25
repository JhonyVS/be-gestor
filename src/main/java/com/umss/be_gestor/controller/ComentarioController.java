package com.umss.be_gestor.controller;

import com.umss.be_gestor.dto.ComentarioDTO;
import com.umss.be_gestor.service.ComentarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping("/create")
    public ResponseEntity<ComentarioDTO> guardarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO guardado = comentarioService.guardarComentario(comentarioDTO);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping("/proyecto/{idProyecto}/detallado")
    public ResponseEntity<List<ComentarioDTO>> listarComentariosConAutoresPorProyecto(@PathVariable UUID idProyecto) {
        List<ComentarioDTO> comentarios = comentarioService.listarComentariosConAutoresPorProyecto(idProyecto);
        return ResponseEntity.ok(comentarios);
    }

}
