package com.umss.be_gestor.service;

import com.umss.be_gestor.dto.ProyectoDTO;
import com.umss.be_gestor.model.ProyectoModel;
import com.umss.be_gestor.model.UsuarioModel;
import com.umss.be_gestor.repository.ProyectoRepository;
import com.umss.be_gestor.util.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProyectoService {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoService.class);

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public ApiResponse<List<ProyectoModel>> getAllProyectos() {
        List<ProyectoModel> proyectos = proyectoRepository.findAll();
        return new ApiResponse<>(true, "Lista de proyectos obtenida correctamente", proyectos);
    }

    public ApiResponse<List<ProyectoDTO>> findAll() {
        List<ProyectoDTO> proyectos = proyectoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Lista de proyectos obtenida correctamente", proyectos);
    }

    public ProyectoModel findProyectoModelById(Long id) {
        Optional<ProyectoModel> proyectoModel = proyectoRepository.findById(id);
        return proyectoModel.orElse(null);
    }

    public ApiResponse<ProyectoDTO> findById(Long id) {
        Optional<ProyectoModel> proyectoOptional = proyectoRepository.findById(id);
        if (proyectoOptional.isPresent()) {
            ProyectoDTO proyectoDTO = convertToDTO(proyectoOptional.get());
            return new ApiResponse<>(true, "Proyecto encontrado", proyectoDTO);
        } else {
            return new ApiResponse<>(false, "Proyecto no encontrado", null);
        }
    }

    public ApiResponse<ProyectoDTO> crearProyecto(ProyectoDTO proyectoDto) {
        ProyectoModel proyectoModel = convertToEntity(proyectoDto);
    
        try {
            proyectoRepository.save(proyectoModel);
            return new ApiResponse<>(true, "Proyecto registrado correctamente", proyectoDto);
        } catch (Exception e) {
            logger.error("Error al guardar proyecto: " + e.getMessage());
            return new ApiResponse<>(false, "Error al guardar proyecto: " + e.getMessage(), null);
        }
    }

    public ApiResponse<ProyectoDTO> updateProyecto(Long id, ProyectoDTO proyectoDto) {
        Optional<ProyectoModel> proyectoOptional = proyectoRepository.findById(id);

        if (proyectoOptional.isPresent()) {
            ProyectoModel proyectoModel = proyectoOptional.get();
            
            // Solo actualiza los campos si se proporcionan en el DTO
            if (proyectoDto.getNombre() != null) {
                proyectoModel.setNombre(proyectoDto.getNombre());
            }
            if (proyectoDto.getDescripcion() != null) {
                proyectoModel.setDescripcion(proyectoDto.getDescripcion());
            }
            if (proyectoDto.getFecha_inicio() != null) {
                proyectoModel.setFecha_inicio(proyectoDto.getFecha_inicio());
            }
            if (proyectoDto.getFecha_final() != null) {
                proyectoModel.setFecha_final(proyectoDto.getFecha_final());
            }
            if (proyectoDto.getIdProjectManager() != null) {
                proyectoModel.setProjectManager(usuarioService.findUsuarioModelById(proyectoDto.getIdProjectManager()));
            }
            
            proyectoModel.setUpdateAt(LocalDateTime.now());

            ProyectoModel updatedProyecto = proyectoRepository.save(proyectoModel);
            ProyectoDTO updatedProyectoDTO = convertToDTO(updatedProyecto);
            return new ApiResponse<>(true, "Proyecto actualizado correctamente", updatedProyectoDTO);
        } else {
            return new ApiResponse<>(false, "Proyecto no encontrado", null);
        }
    }

    public ApiResponse<Void> deleteById(Long id) {
        if (proyectoRepository.existsById(id)) {
            proyectoRepository.deleteById(id);
            return new ApiResponse<>(true, "Proyecto eliminado correctamente", null);
        } else {
            return new ApiResponse<>(false, "Proyecto no encontrado", null);
        }
    }

    private ProyectoDTO convertToDTO(ProyectoModel proyectoModel) {
        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setId(proyectoModel.getId());
        proyectoDTO.setNombre(proyectoModel.getNombre());
        proyectoDTO.setDescripcion(proyectoModel.getDescripcion());
        proyectoDTO.setFecha_inicio(proyectoModel.getFecha_inicio());
        proyectoDTO.setFecha_final(proyectoModel.getFecha_final());
        proyectoDTO.setIdProjectManager(proyectoModel.getProjectManager().getId());
        return proyectoDTO;
    }

    private ProyectoModel convertToEntity(ProyectoDTO proyectoDTO) {
        ProyectoModel proyectoModel = new ProyectoModel();
        proyectoModel.setId(proyectoDTO.getId());
        proyectoModel.setNombre(proyectoDTO.getNombre());
        proyectoModel.setDescripcion(proyectoDTO.getDescripcion());
        proyectoModel.setFecha_inicio(proyectoDTO.getFecha_inicio());
        proyectoModel.setFecha_final(proyectoDTO.getFecha_final());
        proyectoModel.setProjectManager(usuarioService.findUsuarioModelById(proyectoDTO.getIdProjectManager()));
        return proyectoModel;
    }
}
