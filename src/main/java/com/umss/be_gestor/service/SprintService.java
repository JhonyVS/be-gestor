package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.SprintDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Sprint;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.repository.SprintRepository;
import com.umss.be_gestor.repository.ProyectoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<SprintDTO> getAllSprints() {
        return sprintRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Sprint> getFullSprints() {
        return sprintRepository.findAll();
    }

    public SprintDTO getSprintById(UUID id) {
        Sprint sprint = sprintRepository.findById(id).orElse(null);
        if (sprint == null) {
            throw new NotFoundException("Sprint", id.toString());
        }
        return convertToDTO(sprint);
    }

    public SprintDTO createSprint(SprintDTO sprintDTO) {
        Sprint sprint = convertToEntity(sprintDTO);
        sprint.setCreatedAt(LocalDateTime.now());
        sprint.setUpdatedAt(LocalDateTime.now());
        sprint.setActivado(true); // Inicializar activado como true
        sprint = sprintRepository.save(sprint);
        return convertToDTO(sprint);
    }

    public SprintDTO updateSprint(UUID id, SprintDTO sprintDTO) {
        Sprint sprint = sprintRepository.findById(id).orElse(null);
        if (sprint == null) {
            throw new NotFoundException("Sprint", id.toString());
        }

        // Actualización parcial de los campos
        if (sprintDTO.getNumeroSprint() != null) {
            sprint.setNumeroSprint(sprintDTO.getNumeroSprint());
        }
        if (sprintDTO.getProyectoId() != null) {
            Proyecto proyecto = proyectoRepository.findById(sprintDTO.getProyectoId()).orElse(null);
            if (proyecto == null) {
                throw new NotFoundException("Proyecto", sprintDTO.getProyectoId().toString());
            }
            sprint.setProyecto(proyecto);
        }
        if (sprintDTO.getActivado() != null) {
            sprint.setActivado(sprintDTO.getActivado());
        }

        sprint.setUpdatedAt(LocalDateTime.now());
        sprint = sprintRepository.save(sprint);
        return convertToDTO(sprint);
    }

    public void deleteSprint(UUID id) {
        Sprint sprint = sprintRepository.findById(id).orElse(null);
        if (sprint == null) {
            throw new NotFoundException("Sprint", id.toString());
        }
        sprintRepository.delete(sprint);
    }

    private SprintDTO convertToDTO(Sprint sprint) {
        SprintDTO sprintDTO = new SprintDTO();
        sprintDTO.setId(sprint.getId());
        sprintDTO.setNumeroSprint(sprint.getNumeroSprint());
        sprintDTO.setProyectoId(sprint.getProyecto().getId());
        sprintDTO.setActivado(sprint.getActivado());
        return sprintDTO;
    }

    private Sprint convertToEntity(SprintDTO sprintDTO) {
        Sprint sprint = new Sprint();
        sprint.setNumeroSprint(sprintDTO.getNumeroSprint());

        Proyecto proyecto = proyectoRepository.findById(sprintDTO.getProyectoId()).orElse(null);
        if (proyecto == null) {
            throw new NotFoundException("Proyecto", sprintDTO.getProyectoId().toString());
        }
        sprint.setProyecto(proyecto);

        sprint.setActivado(sprintDTO.getActivado());
        return sprint;
    }
}
