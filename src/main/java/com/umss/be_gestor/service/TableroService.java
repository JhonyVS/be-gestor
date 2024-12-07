package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.TableroDTO;
import com.umss.be_gestor.dto.TarjetaDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Tablero;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.model.Workspace;
import com.umss.be_gestor.repository.TableroRepository;
import com.umss.be_gestor.repository.ProyectoRepository;
import com.umss.be_gestor.repository.WorkspaceRepository;
import com.umss.be_gestor.util.DTOConverter;

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

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public List<TableroDTO> getAllTableros() {
        return tableroRepository.findAll().stream()
                .map(DTOConverter::convertToDTO)
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
        return DTOConverter.convertToDTO(tablero);
    }

    public TableroDTO createTablero(TableroDTO tableroDTO) {
        Tablero tablero = DTOConverter.convertToEntity(tableroDTO,proyectoRepository,workspaceRepository);
        tablero.setCreatedAt(LocalDateTime.now());
        tablero.setUpdatedAt(LocalDateTime.now());
        tablero.setActivado(true);
        tablero = tableroRepository.save(tablero);
        return DTOConverter.convertToDTO(tablero);
    }

    public TableroDTO updateTablero(UUID id, TableroDTO tableroDTO) {
        Tablero tablero = tableroRepository.findById(id).orElse(null);
        if (tablero == null) {
            throw new NotFoundException("Tablero", id.toString());
        }

        if (tableroDTO.getProyectoId() != null) {
            Proyecto proyecto = proyectoRepository.findById(tableroDTO.getProyectoId()).orElse(null);
            if (proyecto == null) {
                throw new NotFoundException("Proyecto", tableroDTO.getProyectoId().toString());
            }
            tablero.setProyecto(proyecto);
        }

        if (tableroDTO.getWorkspaceId() != null) {
            Workspace workspace = workspaceRepository.findById(tableroDTO.getWorkspaceId()).orElse(null);
            if (workspace == null) {
                throw new NotFoundException("Workspace", tableroDTO.getWorkspaceId().toString());
            }
            tablero.setWorkspace(workspace);
        }

        if (tableroDTO.getTitulo() != null) {
            tablero.setTitulo(tableroDTO.getTitulo());
        }

        if (tableroDTO.getDescripcion() != null) {
            tablero.setDescripcion(tableroDTO.getDescripcion());
        }

        if (tableroDTO.getActivado() != null) {
            tablero.setActivado(tableroDTO.getActivado());
        }

        tablero.setUpdatedAt(LocalDateTime.now());
        tablero = tableroRepository.save(tablero);
        return DTOConverter.convertToDTO(tablero);
    }

    public void deleteTablero(UUID id) {
        Tablero tablero = tableroRepository.findById(id).orElse(null);
        if (tablero == null) {
            throw new NotFoundException("Tablero", id.toString());
        }
        tableroRepository.delete(tablero);
    }

    

    public List<TarjetaDTO> getTarjetasByTableroId(UUID tableroId) {
        Tablero tablero = tableroRepository.findById(tableroId)
            .orElseThrow(() -> new NotFoundException("Tablero no encontrado", null));

        return tablero.getTarjetas().stream()
            .map(DTOConverter::convertToDTO)
            .collect(Collectors.toList());
    }



}
