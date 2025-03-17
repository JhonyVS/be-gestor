package com.umss.be_gestor.record;

import java.time.LocalDateTime;
import java.util.List;

public record GenericResponse<T>(
    LocalDateTime timestamps, 
    String message,
    List<T> data

) {
    
}
