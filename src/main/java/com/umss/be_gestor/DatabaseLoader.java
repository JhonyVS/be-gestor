package com.umss.be_gestor;

import com.github.javafaker.Faker;
import com.umss.be_gestor.repository.*;
import com.umss.be_gestor.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseLoader {

        @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);

    // array de tareas
    private String[] tareasDevs= {
        "Definir requisitos",
        "Crear mockups",
        "Desarrollar backend",
        "Desarrollar frontend",
        "Realizar pruebas unitarias",
        "Integrar frontend y backend",
        "Implementar autenticación",
        "Configurar base de datos",
        "Escribir documentación técnica",
        "Optimizar el código",
        "Configurar CI/CD",
        "Desplegar en ambiente de pruebas",
        "Revisar feedback de QA",
        "Corregir errores",
        "Desplegar en producción",
        "Capacitar al usuario final",
        "Monitorear rendimiento",
        "Gestionar incidencias",
        "Actualizar documentación",
        "Preparar informe final"
    };


    // array de tareas del project manager
    List<String> tasks = Arrays.asList(
            "Planificación inicial del proyecto",
            "Asignación de recursos",
            "Revisión de requisitos",
            "Creación del cronograma del proyecto",
            "Gestión de riesgos",
            "Reunión de revisión de sprint",
            "Monitoreo del progreso",
            "Gestión de comunicación con el cliente",
            "Evaluación de entregables",
            "Retrospectiva del proyecto",
            "Control de presupuesto del proyecto",
            "Definición de hitos importantes",
            "Ajuste de objetivos según cambios de alcance",
            "Identificación de cuellos de botella",
            "Facilitación de reuniones diarias (stand-ups)",
            "Coordinación de tareas con otros departamentos",
            "Revisión de KPIs y métricas del proyecto",
            "Actualización del plan de riesgos",
            "Gestión de expectativas de los interesados",
            "Documentación del progreso y resultados del proyecto"
        );

    // Vector de nombres de tableros
    private String[] tarjetasArray = {
        "ToDo",
        "Working",
        "Done",
        "Review",
        "Backlog"
    };

    // array de historias
    private String[] historias = {
            "Como usuario, quiero iniciar sesión para acceder a mi cuenta personal",
            "Como usuario, quiero recuperar mi contraseña cuando la olvide",
            "Como cliente, quiero buscar productos por categoría",
            "Como cliente, quiero filtrar productos por precio",
            "Como cliente, quiero ver detalles completos del producto",
            "Como cliente, quiero agregar productos al carrito de compras",
            "Como cliente, quiero eliminar items del carrito",
            "Como cliente, quiero ver el total de mi compra antes de pagar",
            "Como cliente, quiero seleccionar método de envío",
            "Como cliente, quiero aplicar cupones de descuento",
            "Como cliente, quiero ver el historial de mis pedidos",
            "Como cliente, quiero cancelar un pedido antes de que sea enviado",
            "Como usuario, quiero editar mi información personal",
            "Como usuario, quiero suscribirme al newsletter",
            "Como cliente, quiero valorar productos comprados",
            "Como cliente, quiero escribir reseñas de productos",
            "Como usuario, quiero guardar productos en una lista de deseos",
            "Como cliente, quiero comparar productos similares",
            "Como usuario, quiero recibir notificaciones de ofertas",
            "Como cliente, quiero ver productos recomendados",
            "Como usuario, quiero cambiar mi dirección de correo electrónico",
            "Como cliente, quiero ver el estado de mi pedido en tiempo real",
            "Como usuario, quiero descargar facturas de mis compras",
            "Como cliente, quiero contactar al servicio al cliente",
            "Como usuario, quiero ver preguntas frecuentes antes de contactar soporte",
            "Como cliente, quiero programar entregas recurrentes",
            "Como usuario, quiero compartir productos en redes sociales",
            "Como cliente, quiero ver disponibilidad de productos en tiendas físicas",
            "Como usuario, quiero configurar preferencias de notificaciones",
            "Como cliente, quiero pagar con múltiples métodos de pago"
        };

    @Bean
    CommandLineRunner initDatabase(
            IUsuarioRepository usuarioRepository,
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
            ProductBacklogRepository productBacklogRepository,
            WorkspaceRepository workspaceRepository,
            EstadoTareaRepository estadoTareaRepository
    ) {
        return args -> {
            Faker faker = new Faker();

            Set<String> usernames = new HashSet<>();

            // Poblar Usuarios
            if (usuarioRepository.count() == 0) {
                for (int i = 0; i < 40; i++) {
                    Usuario usuario = new Usuario();
                    String username;

                    // Generar un username único
                    do {
                        username = faker.name().firstName();
                    } while (usernames.contains(username));

                    usernames.add(username);  // Agregar el username al conjunto de únicos

                    usuario.setNombres(username);
                    usuario.setApellidos(faker.name().lastName());
                    
                    Date date = faker.date().birthday();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    usuario.setNacimiento(localDate);
                    
                    usuario.setEmail(faker.internet().emailAddress());
                    usuario.setTelefono(faker.phoneNumber().phoneNumber());
                    usuario.setUsername(username);
                    usuario.setPassword(passwordEncoder.encode("123"));
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
                Random r = new Random();
                int num;
                int num2;
                int aux;
                for (Usuario usuario : usuarios) {
                    num = r.nextInt(2)+1;
                    for (int i = 0; i < num; i++) {
                        Proyecto proyecto = new Proyecto();
                        proyecto.setNombre(faker.company().name());
                        proyecto.setDescripcion(faker.lorem().sentence());
                        proyecto.setFechaInicio(LocalDate.now().minusMonths(faker.number().numberBetween(1, 12)));
                        proyecto.setFechaFinal(LocalDate.now().plusMonths(faker.number().numberBetween(1, 12)));
                        proyecto.setProjectManager(usuario);
                        proyecto.setActivado(true);
                        proyecto.setUpdatedAt(LocalDateTime.now());
                        proyecto.setCreatedAt(LocalDateTime.now());
                        proyectoRepository.save(proyecto);

                        ProductBacklog productBacklog = new ProductBacklog();
                        productBacklog.setProyecto(proyecto);
                        productBacklog.setActivado(true);
                        productBacklog.setCreatedAt(LocalDateTime.now());
                        productBacklog.setUpdatedAt(LocalDateTime.now());
                        productBacklogRepository.save(productBacklog);

                        aux = r.nextInt(4)+1;
                        for (int j = 0; j < aux; j++) {
                            Sprint sprint = new Sprint();
                            sprint.setProyecto(proyecto);
                            sprint.setNumeroSprint(j+1);
                            sprint.setActivado(true);
                            sprint.setUpdatedAt(LocalDateTime.now());
                            sprint.setCreatedAt(LocalDateTime.now());
                            sprintRepository.save(sprint);

                            num2 = r.nextInt(2)+1;

                            for (int k = 0; k < num2; k++) {
                                SprintBacklog sprintBacklog = new SprintBacklog();
                                sprintBacklog.setSprint(sprint);
                                sprintBacklog.setActivado(true);
                                sprintBacklog.setCreatedAt(LocalDateTime.now());
                                sprintBacklog.setUpdatedAt(LocalDateTime.now());
                                sprintBacklogRepository.save(sprintBacklog);
                            }


                        }     


                    }
                }
            }

            // Poblar ProductBacklogs
            // if (productBacklogRepository.count() == 0) {
            //     List<Proyecto> proyectos = proyectoRepository.findAll();
            //     for (Proyecto proyecto : proyectos) {
            //         ProductBacklog productBacklog = new ProductBacklog();
            //         productBacklog.setProyecto(proyecto);
            //         productBacklog.setActivado(true);
            //         productBacklog.setCreatedAt(LocalDateTime.now());
            //         productBacklog.setUpdatedAt(LocalDateTime.now());
            //         productBacklogRepository.save(productBacklog);
            //     }
            // }

            // Poblar Equipos
            if (equipoRepository.count() == 0) {
                List<Proyecto> proyectos = proyectoRepository.findAll();
                for (Proyecto proyecto : proyectos) {
                    Equipo equipo = new Equipo();
                    equipo.setNombre(faker.team().name());
                    equipo.setActivado(true);
                    equipo.setProyecto(proyecto);
                    equipo.setCapitan(proyecto.getProjectManager());
                    equipo.setUpdatedAt(LocalDateTime.now());
                    equipo.setCreatedAt(LocalDateTime.now());
                    equipoRepository.save(equipo);
                }
            }


            // Poblar Roles
            if (rolRepository.count() == 0) {
                String[] roles = {"Scrum Master","UX Designer", "Developer", "QA Tester", "Product Owner"};
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
                Random r = new Random();
                int num;

                for (Equipo equipo  : equipos) {
                    num = r.nextInt(4)+2; // Número aleatorio de equipos en los que el usuario participará, minimo 2

                    for (int i = 0; i < num; i++) {
                        Usuario usuario = usuarios.get(faker.number().numberBetween(0, usuarios.size()));
                        //la condicion es para asegurar solamente un scrum master por equipo
                        Rol rol = i == 0 ? roles.get(0) : roles.get(faker.number().numberBetween(1, roles.size()));

                        // Verifica si ya existe un miembro con la misma combinación de usuario y equipo
                        if (!miembroRepository.existsByUsuarioAndEquipo(usuario, equipo)) {
                            Miembro miembro = new Miembro();
                            miembro.setUsuario(usuario);
                            miembro.setEquipo(equipo);
                            miembro.setRol(rol); // Se asigna un rol aleatorio
                            miembro.setActivado(true);
                            miembro.setCreatedAt(LocalDateTime.now());
                            miembro.setUpdatedAt(LocalDateTime.now());
                            miembroRepository.save(miembro);
                        }
                    }
                }
            }

            // Poblar Prioridades
            if (prioridadRepository.count() == 0) {
                for (int i = 0; i < 4; i++) {
                    Prioridad prioridad = new Prioridad();
                    prioridad.setTitulo("Prioridad " + (i + 1));
                    prioridad.setActivado(true);
                    prioridad.setUpdatedAt(LocalDateTime.now());
                    prioridad.setCreatedAt(LocalDateTime.now());
                    prioridadRepository.save(prioridad);
                }
            }

            // Poblar Sprints de proyectos de equipos
            // if (sprintRepository.count() == 0) {
            //     int num=0;
            //     Random r = new Random();
            //     List<Proyecto> proyectos = proyectoRepository.findAll();
            //     for (Proyecto proyecto : proyectos) {
            //         int aux = new Random().nextInt(4)+1;
            //         for (int i = 0; i < aux; i++) {
            //             Sprint sprint = new Sprint();
            //             sprint.setProyecto(proyecto);
            //             sprint.setNumeroSprint(i+1);
            //             sprint.setActivado(true);
            //             sprint.setUpdatedAt(LocalDateTime.now());
            //             sprint.setCreatedAt(LocalDateTime.now());
            //             sprintRepository.save(sprint);
            //         }                    
            //     }
            // }

            // Poblar SprintBacklogs
            // if (sprintBacklogRepository.count() == 0) {
            //     List<Sprint> sprints = sprintRepository.findAll();
            //     Random r = new Random();
            //     int num;
            //     for (Sprint sprint : sprints) {
            //         num = r.nextInt(2)+1;
            //         for (int i = 0; i < num; i++) {
            //             SprintBacklog sprintBacklog = new SprintBacklog();
            //             sprintBacklog.setSprint(sprint);
            //             sprintBacklog.setActivado(true);
            //             sprintBacklog.setCreatedAt(LocalDateTime.now());
            //             sprintBacklog.setUpdatedAt(LocalDateTime.now());
            //             sprintBacklogRepository.save(sprintBacklog);
            //         }
            //     }
            // }
            

            // Poblar Historias
            if (historiaRepository.count() == 0) {
                List<Prioridad> prioridades = prioridadRepository.findAll();
                List<ProductBacklog> pbl = productBacklogRepository.findAll();
                for (ProductBacklog pb : pbl) {
                    for (int i = 0; i < 12; i++) {
                        Historia historia = new Historia();
                        historia.setCodigo("HU"+(i+1));
                        historia.setTitulo(historias[faker.number().numberBetween(0, historias.length)]);
                        historia.setDescripcion(faker.lorem().sentence());
                        historia.setPrioridad(prioridades.get(faker.number().numberBetween(0, prioridades.size())));
                        historia.setProductBacklog(pb);
                        historia.setActivado(true);
                        historia.setUpdatedAt(LocalDateTime.now());
                        historia.setCreatedAt(LocalDateTime.now());
                        historiaRepository.save(historia);
                    }
                }
            }

             // Poblar AsignarHistorias
            if (asignarHistoriaRepository.count() == 0) {
                List<SprintBacklog> spb = sprintBacklogRepository.findAll();
                List<Historia> historias = historiaRepository.findAll();
                for (int i = 0; i < historias.size(); i++) {
                    AsignarHistoria asignarHistoria = new AsignarHistoria();
                    asignarHistoria.setSprintBacklog(spb.get(faker.number().numberBetween(0, spb.size())));
                    asignarHistoria.setHistoria(historias.get(i));
                    asignarHistoria.setActivado(true);
                    asignarHistoria.setCreatedAt(LocalDateTime.now());
                    asignarHistoria.setUpdatedAt(LocalDateTime.now());
                    asignarHistoriaRepository.save(asignarHistoria);
                }
            }

            

            // poblamos los estados de las tareas
            if(estadoTareaRepository.count() == 0){
                EstadoTarea et1 = new EstadoTarea();
                et1.setNombre("Pendiente");
                EstadoTarea et2 = new EstadoTarea();
                et2.setNombre("Trabajando");
                EstadoTarea et3 = new EstadoTarea();
                et3.setNombre("Terminado");
                estadoTareaRepository.save(et1);
                estadoTareaRepository.save(et2);
                estadoTareaRepository.save(et3);
            }



            // Poblar Tableros de proyectos de los devs
            if (tableroRepository.count() == 0) {
                List<Proyecto> proyectos = proyectoRepository.findAll();
                List<EstadoTarea> ets = estadoTareaRepository.findAll();
                //List<Historia> historias = historiaRepository.findAll();
                List<Equipo> equipo;
                Random r = new Random();
                Tablero tablero;
                Tarjeta tarjeta;
                Tarea tarea;
                int num;
                int num2;
                int aux;
                for (Proyecto proyecto: proyectos) {
                    num = r.nextInt(1)+1;// minimo 1 tablero
                    for (int i = 0; i < num; i++) {
                        tablero = new Tablero();
                        tablero.setTitulo(faker.lorem().word());
                        tablero.setDescripcion(faker.lorem().sentence());
                        tablero.setProyecto(proyecto);
                        tablero.setActivado(true);
                        tablero.setUpdatedAt(LocalDateTime.now());
                        tablero.setCreatedAt(LocalDateTime.now());
                        tableroRepository.save(tablero);

                        for (int j = 0; j < 3; j++) {
                            tarjeta = new Tarjeta();
                            tarjeta.setTitulo(tarjetasArray[j]);
                            tarjeta.setDescripcion(faker.lorem().sentence());
                            tarjeta.setTablero(tablero);
                            tarjeta.setActivado(true);
                            tarjeta.setCreatedAt(LocalDateTime.now());
                            tarjeta.setUpdatedAt(LocalDateTime.now());
                            tarjetaRepository.save(tarjeta);   
                            
                            num2 = r.nextInt(10)+5;
                            equipo = equipoRepository.findEquiposByProyectoIdWithRoles(proyecto.getId());
                            List<Usuario> integrantes = new ArrayList<>();
                                for (Equipo eq : equipo) {
                                    for (Miembro integrante : eq.getMiembros()) {
                                        integrantes.add(integrante.getUsuario());
                                    }
                                }
                            List<Historia> hbypb = productBacklogRepository.findHistoriasByProyecto(proyecto.getId());
                            for (int k = 0; k < num2; k++) {
                                tarea = new Tarea();
                                tarea.setTitulo(tareasDevs[faker.number().numberBetween(0,tareasDevs.length)]);
                                tarea.setDescripcion(faker.lorem().sentence());
                                tarea.setTarjeta(tarjeta);
                                tarea.setActivado(true);
                                //tarea.setHistoria(historias.get(faker.number().numberBetween(0, historias.size())));
                                tarea.setHistoria(hbypb.get(faker.number().numberBetween(0, hbypb.size())));
                                productBacklogRepository.findByProyecto(proyecto);
                                tarea.setEstimacion(r.nextInt(5)+2);
                                aux = faker.number().numberBetween(0, 5);
                                tarea.setEstado(ets.get( aux == 0 ? 0 : aux == 1 ? 1:2 )); // de esta forma aseguraremos mas tareas terminadas.
                                // Asignar un usuario aleatorio si hay disponibles
                                if (!integrantes.isEmpty()) {
                                    tarea.setUsuarioAsignado(integrantes.get(r.nextInt(integrantes.size())));
                                }
                                tarea.setCreatedAt(LocalDateTime.now());
                                tarea.setUpdatedAt(LocalDateTime.now());
                                tareaRepository.save(tarea);                      
                            }
                        }

                    }
                }
            }

            

            // // Poblar Tarjetas de proyectos de equipos
            // if (tarjetaRepository.count() == 0) {
            //     List<Tablero> tableros = tableroRepository.findAll();
            //     for (Tablero tablero : tableros) {
            //         for (int i = 0; i < 3; i++) {
            //             Tarjeta tarjeta = new Tarjeta();
            //             tarjeta.setTitulo(tarjetasArray[i]);
            //             tarjeta.setDescripcion(faker.lorem().paragraph());
            //             tarjeta.setTablero(tablero);
            //             tarjeta.setActivado(true);
            //             tarjeta.setCreatedAt(LocalDateTime.now());
            //             tarjeta.setUpdatedAt(LocalDateTime.now());
            //             tarjetaRepository.save(tarjeta);
            //         }
            //     }
            // }

            

            

            // // Poblar Tareas de proyectos de equipos
            // if (tareaRepository.count() == 0) {
            //     List<Tarjeta> tarjetas = tarjetaRepository.findAll();
            //     List<Historia> historias = historiaRepository.findAll();
            //     List<EstadoTarea> ets = estadoTareaRepository.findAll();
            //     Random r = new Random();
            //     int num;
            //     for (Tarjeta tarjeta : tarjetas) {
            //         num = r.nextInt(5);
            //         for (int i = 0; i < num; i++) {
            //             Tarea tarea = new Tarea();
            //             tarea.setTitulo(tareasDevs[faker.number().numberBetween(0,tareasDevs.length)]);
            //             tarea.setDescripcion(faker.lorem().paragraph());
            //             tarea.setTarjeta(tarjeta);
            //             tarea.setHistoria(historias.get(faker.number().numberBetween(0, historias.size())));
            //             tarea.setActivado(true);
            //             tarea.setEstimacion(r.nextInt(5));
            //             tarea.setEstado(ets.get(faker.number().numberBetween(0, ets.size()))); 
            //             tarea.setCreatedAt(LocalDateTime.now());
            //             tarea.setUpdatedAt(LocalDateTime.now());
            //             tareaRepository.save(tarea);
            //         }
            //     }
            // }


            // Poblar workspaces
            if (workspaceRepository.count() == 0) {
                List<Usuario> usuarios = usuarioRepository.findAll();
                for (Usuario usuario : usuarios) {
                    Workspace workspace = new Workspace();
                    workspace.setProjectManager(usuario);
                    workspace.setActivado(true);
                    workspace.setUpdatedAt(LocalDateTime.now());
                    workspace.setCreatedAt(LocalDateTime.now());
                    workspaceRepository.save(workspace);
                }
            

            // Poblar Tableros de workspaces
            //if (tableroRepository.count() == 0) {
                List<Workspace> workspaces = workspaceRepository.findAll();
                List<EstadoTarea> ets = estadoTareaRepository.findAll();
                Random r = new Random();
                Tablero tablero;
                Tarjeta tarjeta;
                Tarea tarea;
                int num;
                int num2;
                for (Workspace workspace: workspaces) {
                    num = r.nextInt(2)+1;
                    for (int i = 0; i < num; i++) {
                        tablero = new Tablero();
                        tablero.setTitulo(faker.lorem().word());
                        tablero.setDescripcion(faker.lorem().sentence());
                        tablero.setWorkspace(workspace);
                        tablero.setActivado(true);
                        tablero.setUpdatedAt(LocalDateTime.now());
                        tablero.setCreatedAt(LocalDateTime.now());
                        tableroRepository.save(tablero);

                        for (int j = 0; j < 3; j++) {
                            tarjeta = new Tarjeta();
                            tarjeta.setTitulo(tarjetasArray[j]);
                            tarjeta.setDescripcion(faker.lorem().sentence());
                            tarjeta.setTablero(tablero);
                            tarjeta.setActivado(true);
                            tarjeta.setCreatedAt(LocalDateTime.now());
                            tarjeta.setUpdatedAt(LocalDateTime.now());
                            tarjetaRepository.save(tarjeta);   
                            
                            num2 = r.nextInt(5)+3;
                            for (int k = 0; k < num2; k++) {
                                tarea = new Tarea();
                                tarea.setTitulo(tasks.get(faker.number().numberBetween(0,tasks.size())));
                                tarea.setDescripcion(faker.lorem().sentence());
                                tarea.setTarjeta(tarjeta);
                                tarea.setActivado(true);
                                tarea.setEstado(ets.get(faker.number().numberBetween(0, ets.size()))); 
                                tarea.setCreatedAt(LocalDateTime.now());
                                tarea.setUpdatedAt(LocalDateTime.now());
                                tareaRepository.save(tarea);                      
                            }
                        }

                    }
                }
            }

            

            logger.info("DatabaseLoader ha terminado existosamente");
            logger.info("Datos cargados correctamente.");

            
        };
    }
}
