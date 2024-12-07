package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.TableroDTO;
import com.umss.be_gestor.dto.TarjetaDTO;
import com.umss.be_gestor.model.Tablero;
import com.umss.be_gestor.service.TableroService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tablero")
public class TableroController {

    @Autowired
    private TableroService tableroService;

    @GetMapping("/all")
    public ResponseEntity<List<TableroDTO>> getAllTableros() {
        return ResponseEntity.ok(tableroService.getAllTableros());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Tablero>> getFullTableros() {
        return ResponseEntity.ok(tableroService.getFullTableros());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TableroDTO> getTableroById(@PathVariable UUID id) {
        return ResponseEntity.ok(tableroService.getTableroById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TableroDTO> createTablero(@RequestBody TableroDTO tableroDTO) {
        return ResponseEntity.status(201).body(tableroService.createTablero(tableroDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TableroDTO> updateTablero(@PathVariable UUID id, @RequestBody TableroDTO tableroDTO) {
        return ResponseEntity.ok(tableroService.updateTablero(id, tableroDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTablero(@PathVariable UUID id) {
        tableroService.deleteTablero(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{tableroId}/tarjetas")
    public ResponseEntity<List<TarjetaDTO>> getTarjetasByTableroId(@PathVariable UUID tableroId) {
        List<TarjetaDTO> tarjetas = tableroService.getTarjetasByTableroId(tableroId);
        return ResponseEntity.ok(tarjetas);
    }
}
