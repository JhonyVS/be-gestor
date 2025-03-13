package com.umss.be_gestor.service;

import com.umss.be_gestor.dto.ComentarioDTO;
import com.umss.be_gestor.dto.UsuarioBasicoDTO;
import com.umss.be_gestor.model.Comentario;
import com.umss.be_gestor.model.Proyecto;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.repository.ComentarioRepository;
import com.umss.be_gestor.repository.ProyectoRepository;
import com.umss.be_gestor.repository.IUsuarioRepository;
import com.umss.be_gestor.util.DTOConverter;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ProyectoRepository proyectoRepository;

    public ComentarioService(ComentarioRepository comentarioRepository, IUsuarioRepository usuarioRepository,
                              ProyectoRepository proyectoRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public ComentarioDTO guardarComentario(ComentarioDTO comentarioDTO) {
        // Validar existencia de Usuario
        Usuario usuario = usuarioRepository.findById(comentarioDTO.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        UsuarioBasicoDTO usuarioBasicoDTO = DTOConverter.convertirAUsuarioBasicoDTO(usuario);

        // Validar existencia de Proyecto
        Proyecto proyecto = proyectoRepository.findById(comentarioDTO.getIdProyecto())
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        // Crear entidad Comentario
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setProyecto(proyecto);
        comentario.setContenido(comentarioDTO.getContenido());

        // Guardar en el repositorio
        Comentario guardado = comentarioRepository.save(comentario);

        // Convertir a DTO
        return DTOConverter.convertirADTO(guardado,usuarioBasicoDTO);
    }


    public List<ComentarioDTO> listarComentariosConAutoresPorProyecto(UUID idProyecto) {
        return comentarioRepository.findByProyectoId(idProyecto).stream()
                .map(comentario -> {
                    UsuarioBasicoDTO usuarioBasicoDTO = DTOConverter.convertirAUsuarioBasicoDTO(comentario.getUsuario());
                    return new ComentarioDTO(
                        comentario.getId(), // Asegúrate de que el ID esté incluido aquí
                        usuarioBasicoDTO,
                        comentario.getProyecto().getId(),
                        comentario.getContenido(),
                        comentario.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());
    }
    
    
    
    
}
