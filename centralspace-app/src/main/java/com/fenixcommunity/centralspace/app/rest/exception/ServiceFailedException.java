package com.fenixcommunity.centralspace.app.rest.exception;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static lombok.AccessLevel.PRIVATE;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ServiceFailedException extends RuntimeException {

    public ServiceFailedException(String message) {
        super(message);
    }
}
