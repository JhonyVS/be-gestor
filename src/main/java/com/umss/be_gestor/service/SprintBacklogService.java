package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.SprintBacklogDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.SprintBacklog;
import com.umss.be_gestor.model.Sprint;
import com.umss.be_gestor.repository.SprintBacklogRepository;
import com.umss.be_gestor.repository.SprintRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SprintBacklogService {

    @Autowired
    private SprintBacklogRepository sprintBacklogRepository;

    @Autowired
    private SprintRepository sprintRepository;

    public List<SprintBacklogDTO> getAllSprintBacklogs() {
        return sprintBacklogRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SprintBacklog> getFullSprintBacklogs() {
        return sprintBacklogRepository.findAll();
    }

    public SprintBacklogDTO getSprintBacklogById(UUID id) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id).orElse(null);
        if (sprintBacklog == null) {
            throw new NotFoundException("SprintBacklog", id.toString());
        }
        return convertToDTO(sprintBacklog);
    }

    public SprintBacklogDTO createSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog sprintBacklog = convertToEntity(sprintBacklogDTO);
        sprintBacklog.setCreatedAt(LocalDateTime.now());
        sprintBacklog.setUpdatedAt(LocalDateTime.now());
        sprintBacklog.setActivado(true); // Inicializar activado como true
        sprintBacklog = sprintBacklogRepository.save(sprintBacklog);
        return convertToDTO(sprintBacklog);
    }

    public SprintBacklogDTO updateSprintBacklog(UUID id, SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id).orElse(null);
        if (sprintBacklog == null) {
            throw new NotFoundException("SprintBacklog", id.toString());
        }

        // Actualización parcial de los campos
        if (sprintBacklogDTO.getSprintId() != null) {
            Sprint sprint = sprintRepository.findById(sprintBacklogDTO.getSprintId()).orElse(null);
            if (sprint == null) {
                throw new NotFoundException("Sprint", sprintBacklogDTO.getSprintId().toString());
            }
            sprintBacklog.setSprint(sprint);
        }
        if (sprintBacklogDTO.getActivado() != null) {
            sprintBacklog.setActivado(sprintBacklogDTO.getActivado());
        }

        sprintBacklog.setUpdatedAt(LocalDateTime.now());
        sprintBacklog = sprintBacklogRepository.save(sprintBacklog);
        return convertToDTO(sprintBacklog);
    }

    public void deleteSprintBacklog(UUID id) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id).orElse(null);
        if (sprintBacklog == null) {
            throw new NotFoundException("SprintBacklog", id.toString());
        }
        sprintBacklogRepository.delete(sprintBacklog);
    }

    private SprintBacklogDTO convertToDTO(SprintBacklog sprintBacklog) {
        SprintBacklogDTO sprintBacklogDTO = new SprintBacklogDTO();
        sprintBacklogDTO.setId(sprintBacklog.getId());
        sprintBacklogDTO.setSprintId(sprintBacklog.getSprint().getId());
        sprintBacklogDTO.setActivado(sprintBacklog.getActivado());
        return sprintBacklogDTO;
    }

    private SprintBacklog convertToEntity(SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog sprintBacklog = new SprintBacklog();
        
        Sprint sprint = sprintRepository.findById(sprintBacklogDTO.getSprintId()).orElse(null);
        if (sprint == null) {
            throw new NotFoundException("Sprint", sprintBacklogDTO.getSprintId().toString());
        }
        sprintBacklog.setSprint(sprint);

        sprintBacklog.setActivado(sprintBacklogDTO.getActivado());
        return sprintBacklog;
    }
}
