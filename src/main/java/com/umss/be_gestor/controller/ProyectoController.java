package com.umss.be_gestor.controller;

import com.umss.be_gestor.dto.ProyectoDTO;
import com.umss.be_gestor.model.ProyectoModel;
import com.umss.be_gestor.service.ProyectoService;
import com.umss.be_gestor.util.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/full")
	public ResponseEntity<ApiResponse<List<ProyectoModel>>> getAllUsers(){
		ApiResponse<List<ProyectoModel>> proyectos = proyectoService.getAllProyectos();
		return ResponseEntity.status(HttpStatus.OK).body(proyectos);
	}

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProyectoDTO>>> getAllProyectos() {
        ApiResponse<List<ProyectoDTO>> response = proyectoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse<ProyectoDTO>> getProyectoById(@PathVariable Long id) {
        ApiResponse<ProyectoDTO> response = proyectoService.findById(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<ApiResponse<ProyectoDTO>> createProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        ApiResponse<ProyectoDTO> response = proyectoService.crearProyecto(proyectoDTO);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProyectoDTO>> updateProyecto(@PathVariable Long id, @RequestBody ProyectoDTO proyectoDTO) {
        ApiResponse<ProyectoDTO> response = proyectoService.updateProyecto(id, proyectoDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProyecto(@PathVariable Long id) {
        ApiResponse<Void> response = proyectoService.deleteById(id);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
