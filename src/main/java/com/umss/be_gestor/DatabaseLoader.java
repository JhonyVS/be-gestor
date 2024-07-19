package com.umss.be_gestor;

import com.github.javafaker.Faker;
import com.umss.be_gestor.repository.*;
import com.umss.be_gestor.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            ProyectoRepository proyectoRepository,
            EquipoRepository equipoRepository,
            PrioridadRepository prioridadRepository,
            HistoriaRepository historiaRepository,
            TableroRepository tableroRepository,
            TarjetaRepository tarjetaRepository,
            SprintRepository sprintRepository,
            TareaRepository tareaRepository,
            SprintBacklogRepository sprintBacklogRepository,
            RolRepository rolRepository,
            MiembroRepository miembroRepository,
            AsignarHistoriaRepository asignarHistoriaRepository,
            ProductBacklogRepository productBacklogRepository
    ) {
        return args -> {
            Faker faker = new Faker();

            // Poblar Usuarios
            if (usuarioRepository.count() == 0) {
                for (int i = 0; i < 100; i++) {
                    Usuario usuario = new Usuario();
                    usuario.setNombres(faker.name().firstName());
                    usuario.setApellidos(faker.name().lastName());
                    Date date = faker.date().birthday();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    usuario.setNacimiento(localDate);
                    usuario.setEmail(faker.internet().emailAddress());
                    usuario.setTelefono(faker.phoneNumber().phoneNumber());
                    usuario.setUsername(faker.name().username());
                    usuario.setPassword("123");
                    usuario.setActivado(true);
                    usuario.setMotivoSuspension(null);
                    usuario.setUpdatedAt(LocalDateTime.now());
                    usuario.setCreatedAt(LocalDateTime.now());
                    usuarioRepository.save(usuario);
                }
            }

            // Poblar Proyectos
            if (proyectoRepository.count() == 0) {
                List<Usuario> usuarios = usuarioRepository.findAll();
                for (int i = 0; i < 50; i++) {
                    Proyecto proyecto = new Proyecto();
                    proyecto.setNombre(faker.company().name());
                    proyecto.setDescripcion(faker.lorem().sentence());
                    proyecto.setFechaInicio(LocalDate.now().minusMonths(faker.number().numberBetween(1, 12)));
                    proyecto.setFechaFinal(LocalDate.now().plusMonths(faker.number().numberBetween(1, 12)));
                    proyecto.setProjectManager(usuarios.get(faker.number().numberBetween(0, usuarios.size())));
                    proyecto.setActivado(true);
                    proyecto.setUpdatedAt(LocalDateTime.now());
                    proyecto.setCreatedAt(LocalDateTime.now());
                    proyectoRepository.save(proyecto);
                }
            }

            // Poblar ProductBacklogs
            if (productBacklogRepository.count() == 0) {
                List<Proyecto> proyectos = proyectoRepository.findAll();
                for (int i = 0; i < 50; i++) {
                    ProductBacklog productBacklog = new ProductBacklog();
                    productBacklog.setProyecto(proyectos.get(faker.number().numberBetween(0, proyectos.size())));
                    productBacklog.setActivado(true);
                    productBacklog.setCreatedAt(LocalDateTime.now());
                    productBacklog.setUpdatedAt(LocalDateTime.now());
                    productBacklogRepository.save(productBacklog);
                }
            }

            // Poblar Equipos
            if (equipoRepository.count() == 0) {
                List<Proyecto> proyectos = proyectoRepository.findAll();
                for (int i = 0; i < 20; i++) {
                    Equipo equipo = new Equipo();
                    equipo.setNombre(faker.team().name());
                    equipo.setActivado(true);
                    equipo.setProyecto(proyectos.get(faker.number().numberBetween(0, proyectos.size())));
                    equipo.setUpdatedAt(LocalDateTime.now());
                    equipo.setCreatedAt(LocalDateTime.now());
                    equipoRepository.save(equipo);
                }
            }

            // Poblar Prioridades
            if (prioridadRepository.count() == 0) {
                for (int i = 0; i < 5; i++) {
                    Prioridad prioridad = new Prioridad();
                    prioridad.setTitulo("Prioridad " + (i + 1));
                    prioridad.setActivado(true);
                    prioridad.setUpdatedAt(LocalDateTime.now());
                    prioridad.setCreatedAt(LocalDateTime.now());
                    prioridadRepository.save(prioridad);
                }
            }

            // Poblar Historias
            if (historiaRepository.count() == 0) {
                List<Prioridad> prioridades = prioridadRepository.findAll();
                List<ProductBacklog> pbl = productBacklogRepository.findAll();
                for (int i = 0; i < 250; i++) {
                    Historia historia = new Historia();
                    historia.setTitulo(faker.lorem().sentence());
                    historia.setDescripcion(faker.lorem().paragraph());
                    historia.setPrioridad(prioridades.get(faker.number().numberBetween(0, prioridades.size())));
                    historia.setProductBacklog(pbl.get(faker.number().numberBetween(0, pbl.size())));
                    historia.setActivado(true);
                    historia.setUpdatedAt(LocalDateTime.now());
                    historia.setCreatedAt(LocalDateTime.now());
                    historiaRepository.save(historia);
                }
            }

            // Poblar Tableros
            if (tableroRepository.count() == 0) {
                List<Proyecto> proyectos = proyectoRepository.findAll();
                for (int i = 0; i < 20; i++) {
                    Tablero tablero = new Tablero();
                    tablero.setTitulo(faker.lorem().word());
                    tablero.setDescripcion(faker.lorem().sentence());
                    tablero.setProyecto(proyectos.get(faker.number().numberBetween(0, proyectos.size())));
                    tablero.setActivado(true);
                    tablero.setUpdatedAt(LocalDateTime.now());
                    tablero.setCreatedAt(LocalDateTime.now());
                    tableroRepository.save(tablero);
                }
            }

            // Poblar Tarjetas
            if (tarjetaRepository.count() == 0) {
                List<Tablero> tableros = tableroRepository.findAll();
                for (int i = 0; i < 100; i++) {
                    Tarjeta tarjeta = new Tarjeta();
                    tarjeta.setTitulo(faker.lorem().sentence());
                    tarjeta.setDescripcion(faker.lorem().paragraph());
                    tarjeta.setTablero(tableros.get(faker.number().numberBetween(0, tableros.size())));
                    tarjeta.setActivado(true);
                    tarjeta.setCreatedAt(LocalDateTime.now());
                    tarjeta.setUpdatedAt(LocalDateTime.now());
                    tarjetaRepository.save(tarjeta);
                }
            }

            // Poblar Sprints
            if (sprintRepository.count() == 0) {
                List<Proyecto> proyectos = proyectoRepository.findAll();
                for (int i = 0; i < 20; i++) {
                    Sprint sprint = new Sprint();
                    sprint.setProyecto(proyectos.get(faker.number().numberBetween(0, proyectos.size())));
                    sprint.setNumeroSprint(faker.number().numberBetween(1, 5));
                    sprint.setActivado(true);
                    sprint.setUpdatedAt(LocalDateTime.now());
                    sprint.setCreatedAt(LocalDateTime.now());
                    sprintRepository.save(sprint);
                }
            }

            // Poblar Tareas
            if (tareaRepository.count() == 0) {
                List<Tarjeta> tarjetas = tarjetaRepository.findAll();
                List<Historia> historias = historiaRepository.findAll();
                for (int i = 0; i < 200; i++) {
                    Tarea tarea = new Tarea();
                    tarea.setTitulo(faker.lorem().sentence());
                    tarea.setDescripcion(faker.lorem().paragraph());
                    tarea.setTarjeta(tarjetas.get(faker.number().numberBetween(0, tarjetas.size())));
                    tarea.setHistoria(historias.get(faker.number().numberBetween(0, historias.size())));
                    tarea.setActivado(true);
                    tarea.setCreatedAt(LocalDateTime.now());
                    tarea.setUpdatedAt(LocalDateTime.now());
                    tareaRepository.save(tarea);
                }
            }

            // Poblar SprintBacklogs
            if (sprintBacklogRepository.count() == 0) {
                List<Sprint> sprints = sprintRepository.findAll();
                for (int i = 0; i < 50; i++) {
                    SprintBacklog sprintBacklog = new SprintBacklog();
                    sprintBacklog.setSprint(sprints.get(faker.number().numberBetween(0, sprints.size())));
                    sprintBacklog.setActivado(true);
                    sprintBacklog.setCreatedAt(LocalDateTime.now());
                    sprintBacklog.setUpdatedAt(LocalDateTime.now());
                    sprintBacklogRepository.save(sprintBacklog);
                }
            }

            // Poblar Roles
            if (rolRepository.count() == 0) {
                String[] roles = {"Admin", "Desarrollador", "Tester", "Scrum Master"};
                for (String roleName : roles) {
                    Rol rol = new Rol();
                    rol.setNombre(roleName);
                    rol.setDescripcion(faker.lorem().sentence());
                    rol.setActivado(true);
                    rol.setCreatedAt(LocalDateTime.now());
                    rol.setUpdatedAt(LocalDateTime.now());
                    rolRepository.save(rol);
                }
            }

            // Poblar Miembros
            if (miembroRepository.count() == 0) {
                List<Usuario> usuarios = usuarioRepository.findAll();
                List<Equipo> equipos = equipoRepository.findAll();
                List<Rol> roles = rolRepository.findAll();
                for (int i = 0; i < 100; i++) {
                    Miembro miembro = new Miembro();
                    miembro.setUsuario(usuarios.get(faker.number().numberBetween(0, usuarios.size())));
                    miembro.setEquipo(equipos.get(faker.number().numberBetween(0, equipos.size())));
                    miembro.setRol(roles.get(faker.number().numberBetween(0, roles.size())));
                    miembro.setActivado(true);
                    miembro.setCreatedAt(LocalDateTime.now());
                    miembro.setUpdatedAt(LocalDateTime.now());
                    miembroRepository.save(miembro);
                }
            }

            // Poblar AsignarHistorias
            if (asignarHistoriaRepository.count() == 0) {
                List<SprintBacklog> spb = sprintBacklogRepository.findAll();
                List<Historia> historias = historiaRepository.findAll();
                for (int i = 0; i < 50; i++) {
                    AsignarHistoria asignarHistoria = new AsignarHistoria();
                    asignarHistoria.setSprintBacklog(spb.get(faker.number().numberBetween(0, spb.size())));
                    asignarHistoria.setHistoria(historias.get(faker.number().numberBetween(0, historias.size())));
                    asignarHistoria.setActivado(true);
                    asignarHistoria.setCreatedAt(LocalDateTime.now());
                    asignarHistoria.setUpdatedAt(LocalDateTime.now());
                    asignarHistoriaRepository.save(asignarHistoria);
                }
            }

            
        };
    }
}
