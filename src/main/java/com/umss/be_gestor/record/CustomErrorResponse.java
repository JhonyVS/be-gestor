package com.umss.be_gestor.record;

public record CustomErrorResponse(
    int code,
    String message,
    String path

) {

}
