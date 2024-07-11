package com.umss.be_gestor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String eName, String uuid){
        super(String.format("%s with ID %s not found", eName, uuid));
    }
}
