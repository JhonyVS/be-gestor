package com.umss.be_gestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umss.be_gestor.dto.TarjetaDTO;
import com.umss.be_gestor.model.Tarjeta;
import com.umss.be_gestor.service.TarjetaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tarjeta")
public class TarjetaController {

    @Autowired
    private TarjetaService tarjetaService;

    @GetMapping("/all")
    public ResponseEntity<List<TarjetaDTO>> getAllTarjetas() {
        return ResponseEntity.ok(tarjetaService.getAllTarjetas());
    }

    @GetMapping("/full")
    public ResponseEntity<List<Tarjeta>> getFullTarjetas() {
        return ResponseEntity.ok(tarjetaService.getFullTarjetas());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TarjetaDTO> getTarjetaById(@PathVariable UUID id) {
        return ResponseEntity.ok(tarjetaService.getTarjetaById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TarjetaDTO> createTarjeta(@RequestBody TarjetaDTO tarjetaDTO) {
        return ResponseEntity.status(201).body(tarjetaService.createTarjeta(tarjetaDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TarjetaDTO> updateTarjeta(@PathVariable UUID id, @RequestBody TarjetaDTO tarjetaDTO) {
        return ResponseEntity.ok(tarjetaService.updateTarjeta(id, tarjetaDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable UUID id) {
        tarjetaService.deleteTarjeta(id);
        return ResponseEntity.noContent().build();
    }
}
