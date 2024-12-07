package com.umss.be_gestor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umss.be_gestor.dto.UsuarioBasicoDTO;
import com.umss.be_gestor.dto.UsuarioDTO;
import com.umss.be_gestor.dto.WorkspaceDTO;
import com.umss.be_gestor.exception.NotFoundException;
import com.umss.be_gestor.model.Usuario;
import com.umss.be_gestor.model.Workspace;
import com.umss.be_gestor.repository.UsuarioRepository;
import com.umss.be_gestor.repository.WorkspaceRepository;
import com.umss.be_gestor.util.DTOConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Usuario> getFullUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioDTO getUsuarioById(UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new NotFoundException("Usuario", id.toString());
        }
        return convertToDTO(usuario);
    }
    public UsuarioDTO getUsuarioByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            throw new NotFoundException("Usuario", username.toString());
        }
        return convertToDTO(usuario);
    }

    public UsuarioDTO getUserDTOByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                                           .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return convertToDTO(usuario);
    }
    public boolean isEmailAvailable(String email) {
        return !usuarioRepository.existsByEmail(email);
    }

    public boolean isUsernameAvailable(String username) {
        return !usuarioRepository.existsByUsername(username);
    }

    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = convertToEntity(usuarioDTO);
        // usuario.setId(UUID.randomUUID()); No es necesario ya que está por defecto en postgres como gen_random_uuid(),
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        //usuario.setPassword(usuarioDTO.getPassword());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setNacimiento(usuarioDTO.getNacimiento());
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        usuario.setActivado(true); // Inicializar activado como true
        usuario = usuarioRepository.save(usuario);
        return convertToDTO(usuario);
    }

    public UsuarioDTO updateUsuario(UUID id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new NotFoundException("Usuario", id.toString());
        }

        // Actualización parcial de los campos
        if (usuarioDTO.getNombres() != null) {
            usuario.setNombres(usuarioDTO.getNombres());
        }
        if (usuarioDTO.getApellidos() != null) {
            usuario.setApellidos(usuarioDTO.getApellidos());
        }
        if (usuarioDTO.getNacimiento() != null) {
            usuario.setNacimiento(usuarioDTO.getNacimiento());
        }
        if (usuarioDTO.getEmail() != null) {
            usuario.setEmail(usuarioDTO.getEmail());
        }
        if (usuarioDTO.getTelefono() != null) {
            usuario.setTelefono(usuarioDTO.getTelefono());
        }
        if (usuarioDTO.getUsername() != null) {
            usuario.setUsername(usuarioDTO.getUsername());
        }
        if (usuarioDTO.getPassword() != null) {
            usuario.setPassword(usuarioDTO.getPassword());
        }
        if (usuarioDTO.isActivado() != null) {
            usuario.setActivado(usuarioDTO.isActivado());
        }
        if (usuarioDTO.getMotivoSuspension() != null) {
            usuario.setMotivoSuspension(usuarioDTO.getMotivoSuspension());
        }

        usuario.setUpdatedAt(LocalDateTime.now());
        usuario = usuarioRepository.save(usuario);
        return convertToDTO(usuario);
    }

    public void deleteUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new NotFoundException("Usuario", id.toString());
        }
        usuarioRepository.delete(usuario);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombres(usuario.getNombres());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setNacimiento(usuario.getNacimiento());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setActivado(usuario.isActivado());
        usuarioDTO.setMotivoSuspension(usuario.getMotivoSuspension());
        return usuarioDTO;
    }

    private Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setNacimiento(usuarioDTO.getNacimiento());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setActivado(usuarioDTO.isActivado());
        usuario.setMotivoSuspension(usuarioDTO.getMotivoSuspension());
        return usuario;
    }


    public List<UUID> getProjectUUIDs(UUID userId) {
        // Lógica para obtener los UUIDs de los proyectos a los que pertenece el usuario
        return usuarioRepository.findProjectUUIDsByUserId(userId); 
    }

    public UsuarioBasicoDTO obtenerUsuarioPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado", null));
        return DTOConverter.convertirAUsuarioBasicoDTO(usuario); 
    }
    
    public WorkspaceDTO getWorkspaceByUserId(UUID userId) {
        Workspace workspace = workspaceRepository.findByProjectManager_Id(userId)
                .orElseThrow(() -> new NotFoundException("Workspace no encontrado para el usuario", null));

        return DTOConverter.convertirWorkspaceADTO(workspace);
    }

}
