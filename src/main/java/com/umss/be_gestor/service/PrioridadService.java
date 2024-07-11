package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.PrioridadDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Prioridad;
import com.umss.be_gestor.repository.PrioridadRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrioridadService {

    @Autowired
    private PrioridadRepository prioridadRepository;

    public List<PrioridadDTO> getAllPrioridades() {
        return prioridadRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Prioridad> getFullPrioridades() {
        return prioridadRepository.findAll();
    }

    public PrioridadDTO getPrioridadById(UUID id) {
        Prioridad prioridad = prioridadRepository.findById(id).orElse(null);
        if (prioridad == null) {
            throw new NotFoundException("Prioridad", id.toString());
        }
        return convertToDTO(prioridad);
    }

    public PrioridadDTO createPrioridad(PrioridadDTO prioridadDTO) {
        Prioridad prioridad = convertToEntity(prioridadDTO);
        prioridad.setCreatedAt(LocalDateTime.now());
        prioridad.setUpdatedAt(LocalDateTime.now());
        prioridad.setActivado(true); // Inicializar activado como true
        prioridad = prioridadRepository.save(prioridad);
        return convertToDTO(prioridad);
    }

    public PrioridadDTO updatePrioridad(UUID id, PrioridadDTO prioridadDTO) {
        Prioridad prioridad = prioridadRepository.findById(id).orElse(null);
        if (prioridad == null) {
            throw new NotFoundException("Prioridad", id.toString());
        }

        // Actualización parcial de los campos
        if (prioridadDTO.getTitulo() != null) {
            prioridad.setTitulo(prioridadDTO.getTitulo());
        }
        if (prioridadDTO.getActivado() != null) {
            prioridad.setActivado(prioridadDTO.getActivado());
        }

        prioridad.setUpdatedAt(LocalDateTime.now());
        prioridad = prioridadRepository.save(prioridad);
        return convertToDTO(prioridad);
    }

    public void deletePrioridad(UUID id) {
        Prioridad prioridad = prioridadRepository.findById(id).orElse(null);
        if (prioridad == null) {
            throw new NotFoundException("Prioridad", id.toString());
        }
        prioridadRepository.delete(prioridad);
    }

    private PrioridadDTO convertToDTO(Prioridad prioridad) {
        PrioridadDTO prioridadDTO = new PrioridadDTO();
        prioridadDTO.setId(prioridad.getId());
        prioridadDTO.setTitulo(prioridad.getTitulo());
        prioridadDTO.setActivado(prioridad.getActivado());
        return prioridadDTO;
    }

    private Prioridad convertToEntity(PrioridadDTO prioridadDTO) {
        Prioridad prioridad = new Prioridad();
        prioridad.setTitulo(prioridadDTO.getTitulo());
        prioridad.setActivado(prioridadDTO.getActivado());
        return prioridad;
    }
}
