package com.umss.be_gestor.service;

import com.umss.be_gestor.dto.EstadoTareaDTO;
import com.umss.be_gestor.model.EstadoTarea;
import com.umss.be_gestor.repository.EstadoTareaRepository;
import com.umss.be_gestor.util.DTOConverter;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstadoTareaService {

    private final EstadoTareaRepository estadoTareaRepository;

    public EstadoTareaService(EstadoTareaRepository estadoTareaRepository) {
        this.estadoTareaRepository = estadoTareaRepository;
    }

    public List<EstadoTarea> getAllEstados() {
        return estadoTareaRepository.findAll();
    }

    public EstadoTarea getEstadoByNombre(String nombre) {
        return estadoTareaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + nombre));
    }

    public EstadoTarea saveEstado(EstadoTareaDTO estadoTareaDTO) {
        return estadoTareaRepository.save(DTOConverter.convertoToEntity(estadoTareaDTO));
    }

    public void deleteEstado(Integer id) {
        if (!estadoTareaRepository.existsById(id)) {
            throw new RuntimeException("No existe el estado con ID: " + id);
        }
        estadoTareaRepository.deleteById(id);
    }
}
