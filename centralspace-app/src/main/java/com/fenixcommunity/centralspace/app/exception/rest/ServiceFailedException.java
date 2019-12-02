package com.fenixcommunity.centralspace.app.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceFailedException extends RuntimeException {

    public ServiceFailedException(String message) {
        super(message);
    }
}
