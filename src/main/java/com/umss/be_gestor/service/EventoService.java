package com.umss.be_gestor.service;

import com.umss.be_gestor.dto.EventoDTO;
import com.umss.be_gestor.model.Evento;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.repository.EventoRepository;
import com.umss.be_gestor.util.DTOConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public EventoDTO guardarEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();

        // Configurar la relación con Proyecto
        Proyecto proyecto = new Proyecto();
        proyecto.setId(eventoDTO.getIdProyecto());
        evento.setProyecto(proyecto);

        // Configurar los demás campos
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        //evento.setFechaFin(eventoDTO.getFechaFin());

        // Guardar el evento en el repositorio
        Evento guardado = eventoRepository.save(evento);

        // Convertir la entidad guardada a DTO y devolverla
        return DTOConverter.convertirADTO(guardado);
    }


    public List<EventoDTO> listarEventosPorProyecto(UUID idProyecto) {
        return eventoRepository.findAll().stream()
                .filter(evento -> evento.getProyecto().getId().equals(idProyecto))
                .map(DTOConverter::convertirADTO)
                .collect(Collectors.toList());
    }

    
    
}
