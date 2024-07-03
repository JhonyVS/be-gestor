package com.umss.be_gestor;

import com.github.javafaker.Faker;
import com.umss.be_gestor.repository.UsuarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.umss.be_gestor.model.UsuarioModel;


@Configuration
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {  // Verifica si la tabla está vacía
                Faker faker = new Faker();
                for (int i = 0; i < 100; i++) {
                    UsuarioModel usuario = new UsuarioModel();
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
                    
                    repository.save(usuario);
                }
            }
        };
    }
}