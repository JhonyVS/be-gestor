package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.TableroDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Tablero;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.repository.TableroRepository;
import com.umss.be_gestor.repository.ProyectoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TableroService {

    @Autowired
    private TableroRepository tableroRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<TableroDTO> getAllTableros() {
        return tableroRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Tablero> getFullTableros() {
        return tableroRepository.findAll();
    }

    public TableroDTO getTableroById(UUID id) {
        Tablero tablero = tableroRepository.findById(id).orElse(null);
        if (tablero == null) {
            throw new NotFoundException("Tablero", id.toString());
        }
        return convertToDTO(tablero);
    }

    public TableroDTO createTablero(TableroDTO tableroDTO) {
        Tablero tablero = convertToEntity(tableroDTO);
        tablero.setCreatedAt(LocalDateTime.now());
        tablero.setUpdatedAt(LocalDateTime.now());
        tablero.setActivado(true); // Inicializar activado como true
        tablero = tableroRepository.save(tablero);
        return convertToDTO(tablero);
    }

    public TableroDTO updateTablero(UUID id, TableroDTO tableroDTO) {
        Tablero tablero = tableroRepository.findById(id).orElse(null);
        if (tablero == null) {
            throw new NotFoundException("Tablero", id.toString());
        }

        // Actualización parcial de los campos
        if (tableroDTO.getTitulo() != null) {
            tablero.setTitulo(tableroDTO.getTitulo());
        }
        if (tableroDTO.getDescripcion() != null) {
            tablero.setDescripcion(tableroDTO.getDescripcion());
        }
        if (tableroDTO.getProyectoId() != null) {
            Proyecto proyecto = proyectoRepository.findById(tableroDTO.getProyectoId()).orElse(null);
            if (proyecto == null) {
                throw new NotFoundException("Proyecto", tableroDTO.getProyectoId().toString());
            }
            tablero.setProyecto(proyecto);
        }
        if (tableroDTO.getActivado() != null) {
            tablero.setActivado(tableroDTO.getActivado());
        }

        tablero.setUpdatedAt(LocalDateTime.now());
        tablero = tableroRepository.save(tablero);
        return convertToDTO(tablero);
    }

    public void deleteTablero(UUID id) {
        Tablero tablero = tableroRepository.findById(id).orElse(null);
        if (tablero == null) {
            throw new NotFoundException("Tablero", id.toString());
        }
        tableroRepository.delete(tablero);
    }

    private TableroDTO convertToDTO(Tablero tablero) {
        TableroDTO tableroDTO = new TableroDTO();
        tableroDTO.setId(tablero.getId());
        tableroDTO.setTitulo(tablero.getTitulo());
        tableroDTO.setDescripcion(tablero.getDescripcion());
        tableroDTO.setProyectoId(tablero.getProyecto().getId());
        tableroDTO.setActivado(tablero.getActivado());
        return tableroDTO;
    }

    private Tablero convertToEntity(TableroDTO tableroDTO) {
        Tablero tablero = new Tablero();
        tablero.setTitulo(tableroDTO.getTitulo());
        tablero.setDescripcion(tableroDTO.getDescripcion());

        Proyecto proyecto = proyectoRepository.findById(tableroDTO.getProyectoId()).orElse(null);
        if (proyecto == null) {
            throw new NotFoundException("Proyecto", tableroDTO.getProyectoId().toString());
        }
        tablero.setProyecto(proyecto);

        tablero.setActivado(tableroDTO.getActivado());
        return tablero;
    }
}
