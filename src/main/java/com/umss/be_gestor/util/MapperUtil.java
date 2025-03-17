package com.umss.be_gestor.util;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<T> source, Class<T> target){
        return source.stream()
                .map(element -> modelMapper.map(element, target))
                .toList();
    }

    public <S, T> T map(S source, Class<T> target){
        return modelMapper.map(source, target);
    }

}
