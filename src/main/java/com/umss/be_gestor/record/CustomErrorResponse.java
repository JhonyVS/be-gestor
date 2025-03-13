package com.umss.be_gestor.record;

import java.time.LocalDateTime;

public record CustomErrorResponse(
    LocalDateTime timestamps, 
    String message,
    String path

) {
    
}
