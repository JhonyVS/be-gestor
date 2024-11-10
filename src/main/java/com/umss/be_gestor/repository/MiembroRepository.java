package com.umss.be_gestor.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umss.be_gestor.model.Equipo;
import com.umss.be_gestor.model.Miembro;
import com.umss.be_gestor.model.MiembroId;
import com.umss.be_gestor.model.Rol;
import com.umss.be_gestor.model.Usuario;

public interface MiembroRepository extends JpaRepository<Miembro, MiembroId> {
    boolean existsByUsuarioAndEquipoAndRol(Usuario usuario, Equipo equipo, Rol rol);

    List<Miembro> findByEquipo_Id(UUID equipoId);
    List<Miembro> findByUsuario_Id(UUID usuarioId);

    boolean existsByUsuarioAndEquipo(Usuario usuario, Equipo equipo);
    List<Miembro> findByEquipoId(UUID equipoId);

}
