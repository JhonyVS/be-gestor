package com.umss.be_gestor.util;

import com.umss.be_gestor.dto.EventoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class EventoUtil {

    /**
     * Ordena una lista de eventos por fecha de inicio ascendente.
     *
     * @param eventos Lista de eventos a ordenar.
     * @return Lista ordenada de eventos.
     */
    public static List<EventoDTO> ordenarPorFechaInicio(List<EventoDTO> eventos) {
        return eventos.stream()
                .sorted((e1, e2) -> e1.getFechaInicio().compareTo(e2.getFechaInicio()))
                .collect(Collectors.toList());
    }
}
