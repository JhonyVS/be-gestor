package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.HistoriaDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Historia;
import com.umss.be_gestor.model.ProductBacklog;
import com.umss.be_gestor.model.Prioridad;
import com.umss.be_gestor.repository.HistoriaRepository;
import com.umss.be_gestor.repository.ProductBacklogRepository;
import com.umss.be_gestor.repository.PrioridadRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HistoriaService {

    @Autowired
    private HistoriaRepository historiaRepository;

    @Autowired
    private ProductBacklogRepository productBacklogRepository;

    @Autowired
    private PrioridadRepository prioridadRepository;

    public List<HistoriaDTO> getAllHistorias() {
        return historiaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Historia> getFullHistorias() {
        return historiaRepository.findAll();
    }

    public HistoriaDTO getHistoriaById(UUID id) {
        Historia historia = historiaRepository.findById(id).orElse(null);
        if (historia == null) {
            throw new NotFoundException("Historia", id.toString());
        }
        return convertToDTO(historia);
    }

    public HistoriaDTO createHistoria(HistoriaDTO historiaDTO) {
        Historia historia = convertToEntity(historiaDTO);
        historia.setCreatedAt(LocalDateTime.now());
        historia.setUpdatedAt(LocalDateTime.now());
        historia.setActivado(true); // Inicializar activado como true
        historia = historiaRepository.save(historia);
        return convertToDTO(historia);
    }

    public HistoriaDTO updateHistoria(UUID id, HistoriaDTO historiaDTO) {
        Historia historia = historiaRepository.findById(id).orElse(null);
        if (historia == null) {
            throw new NotFoundException("Historia", id.toString());
        }

        // Actualización parcial de los campos
        if (historiaDTO.getProductBacklogId() != null) {
            ProductBacklog productBacklog = productBacklogRepository.findById(historiaDTO.getProductBacklogId()).orElse(null);
            if (productBacklog == null) {
                throw new NotFoundException("ProductBacklog", historiaDTO.getProductBacklogId().toString());
            }
            historia.setProductBacklog(productBacklog);
        }
        if (historiaDTO.getPrioridadId() != null) {
            Prioridad prioridad = prioridadRepository.findById(historiaDTO.getPrioridadId()).orElse(null);
            if (prioridad == null) {
                throw new NotFoundException("Prioridad", historiaDTO.getPrioridadId().toString());
            }
            historia.setPrioridad(prioridad);
        }
        if (historiaDTO.getTitulo() != null) {
            historia.setTitulo(historiaDTO.getTitulo());
        }
        if (historiaDTO.getDescripcion() != null) {
            historia.setDescripcion(historiaDTO.getDescripcion());
        }
        if (historiaDTO.getEstimacion() != null) {
            historia.setEstimacion(historiaDTO.getEstimacion());
        }
        if (historiaDTO.getActivado() != null) {
            historia.setActivado(historiaDTO.getActivado());
        }

        historia.setUpdatedAt(LocalDateTime.now());
        historia = historiaRepository.save(historia);
        return convertToDTO(historia);
    }

    public void deleteHistoria(UUID id) {
        Historia historia = historiaRepository.findById(id).orElse(null);
        if (historia == null) {
            throw new NotFoundException("Historia", id.toString());
        }
        historiaRepository.delete(historia);
    }

    private HistoriaDTO convertToDTO(Historia historia) {
        HistoriaDTO historiaDTO = new HistoriaDTO();
        historiaDTO.setId(historia.getId());
        historiaDTO.setProductBacklogId(historia.getProductBacklog().getId());
        historiaDTO.setPrioridadId(historia.getPrioridad().getId());
        historiaDTO.setTitulo(historia.getTitulo());
        historiaDTO.setDescripcion(historia.getDescripcion());
        historiaDTO.setEstimacion(historia.getEstimacion());
        historiaDTO.setActivado(historia.getActivado());
        return historiaDTO;
    }

    private Historia convertToEntity(HistoriaDTO historiaDTO) {
        Historia historia = new Historia();
        historia.setTitulo(historiaDTO.getTitulo());
        historia.setDescripcion(historiaDTO.getDescripcion());
        historia.setEstimacion(historiaDTO.getEstimacion());

        if (historiaDTO.getProductBacklogId() != null) {
            ProductBacklog productBacklog = productBacklogRepository.findById(historiaDTO.getProductBacklogId()).orElse(null);
            if (productBacklog == null) {
                throw new NotFoundException("ProductBacklog", historiaDTO.getProductBacklogId().toString());
            }
            historia.setProductBacklog(productBacklog);
        }

        if (historiaDTO.getPrioridadId() != null) {
            Prioridad prioridad = prioridadRepository.findById(historiaDTO.getPrioridadId()).orElse(null);
            if (prioridad == null) {
                throw new NotFoundException("Prioridad", historiaDTO.getPrioridadId().toString());
            }
            historia.setPrioridad(prioridad);
        }

        historia.setActivado(historiaDTO.getActivado());
        return historia;
    }
}
