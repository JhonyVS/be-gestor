package com.umss.be_gestor.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getDefaultModlMapper(){
        return new ModelMapper();
    }
}
