package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.AsignarHistoriaDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.AsignarHistoria;
import com.umss.be_gestor.model.AsignarHistoriaId;
import com.umss.be_gestor.model.SprintBacklog;
import com.umss.be_gestor.model.Historia;
import com.umss.be_gestor.repository.AsignarHistoriaRepository;
import com.umss.be_gestor.repository.SprintBacklogRepository;
import com.umss.be_gestor.repository.HistoriaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AsignarHistoriaService {

    @Autowired
    private AsignarHistoriaRepository asignarHistoriaRepository;

    @Autowired
    private SprintBacklogRepository sprintBacklogRepository;

    @Autowired
    private HistoriaRepository historiaRepository;

    public List<AsignarHistoriaDTO> getAllAsignarHistorias() {
        return asignarHistoriaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AsignarHistoria> getFullAsignarHistorias() {
        return asignarHistoriaRepository.findAll();
    }

    public AsignarHistoriaDTO getAsignarHistoriaById(UUID sprintBacklogId, UUID historiaId) {
        AsignarHistoriaId asignarHistoriaId = new AsignarHistoriaId();
        asignarHistoriaId.setSprintBacklog(sprintBacklogId);
        asignarHistoriaId.setHistoria(historiaId);

        AsignarHistoria asignarHistoria = asignarHistoriaRepository.findById(asignarHistoriaId).orElse(null);
        if (asignarHistoria == null) {
            throw new NotFoundException("AsignarHistoria", sprintBacklogId.toString() + ", " + historiaId.toString());
        }
        return convertToDTO(asignarHistoria);
    }

    public AsignarHistoriaDTO createAsignarHistoria(AsignarHistoriaDTO asignarHistoriaDTO) {
        // Verificar si la combinación de sprintBacklog y historia ya existe
        AsignarHistoriaId asignarHistoriaId = new AsignarHistoriaId(
            asignarHistoriaDTO.getSprintBacklogId(),
            asignarHistoriaDTO.getHistoriaId()
        );

        if (asignarHistoriaRepository.existsById(asignarHistoriaId)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "La historia ya está asignada a este sprint backlog");
        }

        AsignarHistoria asignarHistoria = convertToEntity(asignarHistoriaDTO);
        asignarHistoria.setCreatedAt(LocalDateTime.now());
        asignarHistoria.setUpdatedAt(LocalDateTime.now());
        asignarHistoria.setActivado(true); // Inicializar activado como true
        asignarHistoria = asignarHistoriaRepository.save(asignarHistoria);
        return convertToDTO(asignarHistoria);
    }

    public AsignarHistoriaDTO updateAsignarHistoria(UUID sprintBacklogId, UUID historiaId, AsignarHistoriaDTO asignarHistoriaDTO) {
        AsignarHistoriaId asignarHistoriaId = new AsignarHistoriaId();
        asignarHistoriaId.setSprintBacklog(sprintBacklogId);
        asignarHistoriaId.setHistoria(historiaId);

        AsignarHistoria asignarHistoria = asignarHistoriaRepository.findById(asignarHistoriaId).orElse(null);
        if (asignarHistoria == null) {
            throw new NotFoundException("AsignarHistoria", sprintBacklogId.toString() + ", " + historiaId.toString());
        }

        // Actualización parcial de los campos
        if (asignarHistoriaDTO.getActivado() != null) {
            asignarHistoria.setActivado(asignarHistoriaDTO.getActivado());
        }

        asignarHistoria.setUpdatedAt(LocalDateTime.now());
        asignarHistoria = asignarHistoriaRepository.save(asignarHistoria);
        return convertToDTO(asignarHistoria);
    }

    public void deleteAsignarHistoria(UUID sprintBacklogId, UUID historiaId) {
        AsignarHistoriaId asignarHistoriaId = new AsignarHistoriaId();
        asignarHistoriaId.setSprintBacklog(sprintBacklogId);
        asignarHistoriaId.setHistoria(historiaId);

        AsignarHistoria asignarHistoria = asignarHistoriaRepository.findById(asignarHistoriaId).orElse(null);
        if (asignarHistoria == null) {
            throw new NotFoundException("AsignarHistoria", sprintBacklogId.toString() + ", " + historiaId.toString());
        }
        asignarHistoriaRepository.delete(asignarHistoria);
    }

    private AsignarHistoriaDTO convertToDTO(AsignarHistoria asignarHistoria) {
        AsignarHistoriaDTO asignarHistoriaDTO = new AsignarHistoriaDTO();
        asignarHistoriaDTO.setSprintBacklogId(asignarHistoria.getSprintBacklog().getId());
        asignarHistoriaDTO.setHistoriaId(asignarHistoria.getHistoria().getId());
        asignarHistoriaDTO.setActivado(asignarHistoria.getActivado());
        return asignarHistoriaDTO;
    }

    private AsignarHistoria convertToEntity(AsignarHistoriaDTO asignarHistoriaDTO) {
        AsignarHistoria asignarHistoria = new AsignarHistoria();

        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(asignarHistoriaDTO.getSprintBacklogId()).orElse(null);
        if (sprintBacklog == null) {
            throw new NotFoundException("SprintBacklog", asignarHistoriaDTO.getSprintBacklogId().toString());
        }
        asignarHistoria.setSprintBacklog(sprintBacklog);

        Historia historia = historiaRepository.findById(asignarHistoriaDTO.getHistoriaId()).orElse(null);
        if (historia == null) {
            throw new NotFoundException("Historia", asignarHistoriaDTO.getHistoriaId().toString());
        }
        asignarHistoria.setHistoria(historia);

        asignarHistoria.setActivado(asignarHistoriaDTO.getActivado());
        return asignarHistoria;
    }
}
