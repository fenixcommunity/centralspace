package com.fenixcommunity.centralspace.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    private static final long serialVersionUID = -3257391656581378812L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
