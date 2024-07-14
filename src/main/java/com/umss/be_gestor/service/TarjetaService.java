package com.umss.be_gestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umss.be_gestor.dto.TarjetaDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Tarjeta;
import com.umss.be_gestor.model.Tablero;
import com.umss.be_gestor.repository.TarjetaRepository;
import com.umss.be_gestor.repository.TableroRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private TableroRepository tableroRepository;

    public List<TarjetaDTO> getAllTarjetas() {
        return tarjetaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Tarjeta> getFullTarjetas() {
        return tarjetaRepository.findAll();
    }

    public TarjetaDTO getTarjetaById(UUID id) {
        Tarjeta tarjeta = tarjetaRepository.findById(id).orElse(null);
        if (tarjeta == null) {
            throw new NotFoundException("Tarjeta", id.toString());
        }
        return convertToDTO(tarjeta);
    }

    public TarjetaDTO createTarjeta(TarjetaDTO tarjetaDTO) {
        Tarjeta tarjeta = convertToEntity(tarjetaDTO);
        tarjeta.setCreatedAt(LocalDateTime.now());
        tarjeta.setUpdatedAt(LocalDateTime.now());
        tarjeta.setActivado(true); // Inicializar activado como true
        tarjeta = tarjetaRepository.save(tarjeta);
        return convertToDTO(tarjeta);
    }

    public TarjetaDTO updateTarjeta(UUID id, TarjetaDTO tarjetaDTO) {
        Tarjeta tarjeta = tarjetaRepository.findById(id).orElse(null);
        if (tarjeta == null) {
            throw new NotFoundException("Tarjeta", id.toString());
        }

        // Actualización parcial de los campos
        if (tarjetaDTO.getTitulo() != null) {
            tarjeta.setTitulo(tarjetaDTO.getTitulo());
        }
        if (tarjetaDTO.getDescripcion() != null) {
            tarjeta.setDescripcion(tarjetaDTO.getDescripcion());
        }
        if (tarjetaDTO.getTableroId() != null) {
            Tablero tablero = tableroRepository.findById(tarjetaDTO.getTableroId()).orElse(null);
            if (tablero == null) {
                throw new NotFoundException("Tablero", tarjetaDTO.getTableroId().toString());
            }
            tarjeta.setTablero(tablero);
        }
        if (tarjetaDTO.getActivado() != null) {
            tarjeta.setActivado(tarjetaDTO.getActivado());
        }

        tarjeta.setUpdatedAt(LocalDateTime.now());
        tarjeta = tarjetaRepository.save(tarjeta);
        return convertToDTO(tarjeta);
    }

    public void deleteTarjeta(UUID id) {
        Tarjeta tarjeta = tarjetaRepository.findById(id).orElse(null);
        if (tarjeta == null) {
            throw new NotFoundException("Tarjeta", id.toString());
        }
        tarjetaRepository.delete(tarjeta);
    }

    private TarjetaDTO convertToDTO(Tarjeta tarjeta) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO();
        tarjetaDTO.setId(tarjeta.getId());
        tarjetaDTO.setTitulo(tarjeta.getTitulo());
        tarjetaDTO.setDescripcion(tarjeta.getDescripcion());
        tarjetaDTO.setTableroId(tarjeta.getTablero().getId());
        tarjetaDTO.setActivado(tarjeta.getActivado());
        return tarjetaDTO;
    }

    private Tarjeta convertToEntity(TarjetaDTO tarjetaDTO) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setTitulo(tarjetaDTO.getTitulo());
        tarjeta.setDescripcion(tarjetaDTO.getDescripcion());

        Tablero tablero = tableroRepository.findById(tarjetaDTO.getTableroId()).orElse(null);
        if (tablero == null) {
            throw new NotFoundException("Tablero", tarjetaDTO.getTableroId().toString());
        }
        tarjeta.setTablero(tablero);

        tarjeta.setActivado(tarjetaDTO.getActivado());
        return tarjeta;
    }
}
