package com.fenixcommunity.centralspace.app.rest.exception;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static lombok.AccessLevel.PRIVATE;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
