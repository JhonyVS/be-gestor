package com.umss.be_gestor.model;
import javax.persistence.*;

// import org.springframework.beans.factory.annotation.Autowired;

// import com.umss.be_gestor.repository.EstadoTareaRepository;


@Entity
@Table(name = "estado_tarea")
public class EstadoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true) // Esto asegura que el nombre no sea nulo ni duplicado
    private String nombre;

    // @Autowired
    // private EstadoTareaRepository estadoTareaRepository;



    // public EstadoTarea PENDIENTE;
    // public EstadoTarea TRABAJANDO;
    // public EstadoTarea TERMINADO;

    // public  EstadoTarea getEstadoTareaPendiente(){
    //     if(PENDIENTE == null){
    //         EstadoTarea et = new EstadoTarea();
    //         et.setNombre("Pendiente");
    //         PENDIENTE = estadoTareaRepository.save(et);
    //     }
    //     return PENDIENTE;
    // }

    // public EstadoTarea getEstadoTareaTrabajando(){
    //     if(TRABAJANDO == null){
    //         EstadoTarea et = new EstadoTarea();
    //         et.setNombre("Trabajando");
    //         TRABAJANDO = estadoTareaRepository.save(et);
    //     }
    //     return TRABAJANDO;
    // }
    // public EstadoTarea getEstadoTareaTerminado(){
    //     if(TERMINADO == null){
    //         EstadoTarea et = new EstadoTarea();
    //         et.setNombre("Terminado");
    //         TERMINADO = estadoTareaRepository.save(et);
    //     }
    //     return TERMINADO;
    // }

    public EstadoTarea(){}

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

