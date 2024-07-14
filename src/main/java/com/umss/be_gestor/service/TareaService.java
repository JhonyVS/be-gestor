package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.TareaDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Tarea;
import com.umss.be_gestor.model.Historia;
import com.umss.be_gestor.model.Tarjeta;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.TareaRepository;
import com.umss.be_gestor.repository.HistoriaRepository;
import com.umss.be_gestor.repository.TarjetaRepository;
import com.umss.be_gestor.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private HistoriaRepository historiaRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<TareaDTO> getAllTareas() {
        return tareaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Tarea> getFullTareas() {
        return tareaRepository.findAll();
    }

    public TareaDTO getTareaById(UUID id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea == null) {
            throw new NotFoundException("Tarea", id.toString());
        }
        return convertToDTO(tarea);
    }

    public TareaDTO createTarea(TareaDTO tareaDTO) {
        Tarea tarea = convertToEntity(tareaDTO);
        tarea.setCreatedAt(LocalDateTime.now());
        tarea.setUpdatedAt(LocalDateTime.now());
        tarea.setActivado(true); // Inicializar activado como true
        tarea = tareaRepository.save(tarea);
        return convertToDTO(tarea);
    }

    public TareaDTO updateTarea(UUID id, TareaDTO tareaDTO) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea == null) {
            throw new NotFoundException("Tarea", id.toString());
        }

        // Actualización parcial de los campos
        if (tareaDTO.getTitulo() != null) {
            tarea.setTitulo(tareaDTO.getTitulo());
        }
        if (tareaDTO.getDescripcion() != null) {
            tarea.setDescripcion(tareaDTO.getDescripcion());
        }
        if (tareaDTO.getEstimacion() != null) {
            tarea.setEstimacion(tareaDTO.getEstimacion());
        }
        if (tareaDTO.getHistoriaId() != null) {
            Historia historia = historiaRepository.findById(tareaDTO.getHistoriaId()).orElse(null);
            if (historia == null) {
                throw new NotFoundException("Historia", tareaDTO.getHistoriaId().toString());
            }
            tarea.setHistoria(historia);
        }
        if (tareaDTO.getTarjetaId() != null) {
            Tarjeta tarjeta = tarjetaRepository.findById(tareaDTO.getTarjetaId()).orElse(null);
            if (tarjeta == null) {
                throw new NotFoundException("Tarjeta", tareaDTO.getTarjetaId().toString());
            }
            tarea.setTarjeta(tarjeta);
        }
        if (tareaDTO.getUsuarioAsignadoId() != null) {
            Usuario usuarioAsignado = usuarioRepository.findById(tareaDTO.getUsuarioAsignadoId()).orElse(null);
            if (usuarioAsignado == null) {
                throw new NotFoundException("Usuario", tareaDTO.getUsuarioAsignadoId().toString());
            }
            tarea.setUsuarioAsignado(usuarioAsignado);
        }
        if (tareaDTO.getActivado() != null) {
            tarea.setActivado(tareaDTO.getActivado());
        }

        tarea.setUpdatedAt(LocalDateTime.now());
        tarea = tareaRepository.save(tarea);
        return convertToDTO(tarea);
    }

    public void deleteTarea(UUID id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea == null) {
            throw new NotFoundException("Tarea", id.toString());
        }
        tareaRepository.delete(tarea);
    }

    private TareaDTO convertToDTO(Tarea tarea) {
        TareaDTO tareaDTO = new TareaDTO();
        tareaDTO.setId(tarea.getId());
        tareaDTO.setTitulo(tarea.getTitulo());
        tareaDTO.setDescripcion(tarea.getDescripcion());
        tareaDTO.setEstimacion(tarea.getEstimacion());
        tareaDTO.setHistoriaId(tarea.getHistoria() != null ? tarea.getHistoria().getId() : null);
        tareaDTO.setTarjetaId(tarea.getTarjeta() != null ? tarea.getTarjeta().getId() : null);
        tareaDTO.setUsuarioAsignadoId(tarea.getUsuarioAsignado() != null ? tarea.getUsuarioAsignado().getId() : null);
        tareaDTO.setActivado(tarea.getActivado());
        return tareaDTO;
    }

    private Tarea convertToEntity(TareaDTO tareaDTO) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(tareaDTO.getTitulo());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setEstimacion(tareaDTO.getEstimacion());

        if (tareaDTO.getHistoriaId() != null) {
            Historia historia = historiaRepository.findById(tareaDTO.getHistoriaId()).orElse(null);
            if (historia == null) {
                throw new NotFoundException("Historia", tareaDTO.getHistoriaId().toString());
            }
            tarea.setHistoria(historia);
        }

        if (tareaDTO.getTarjetaId() != null) {
            Tarjeta tarjeta = tarjetaRepository.findById(tareaDTO.getTarjetaId()).orElse(null);
            if (tarjeta == null) {
                throw new NotFoundException("Tarjeta", tareaDTO.getTarjetaId().toString());
            }
            tarea.setTarjeta(tarjeta);
        }

        if (tareaDTO.getUsuarioAsignadoId() != null) {
            Usuario usuarioAsignado = usuarioRepository.findById(tareaDTO.getUsuarioAsignadoId()).orElse(null);
            if (usuarioAsignado == null) {
                throw new NotFoundException("Usuario", tareaDTO.getUsuarioAsignadoId().toString());
            }
            tarea.setUsuarioAsignado(usuarioAsignado);
        }

        tarea.setActivado(tareaDTO.getActivado());
        return tarea;
    }
}
