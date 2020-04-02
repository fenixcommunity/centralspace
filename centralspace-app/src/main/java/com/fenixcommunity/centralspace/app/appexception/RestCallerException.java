package com.fenixcommunity.centralspace.app.appexception;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestCallerException extends RuntimeException {

    public RestCallerException(String message) {
        super(message);
    }

    public RestCallerException(String message, Throwable cause) {
        super(message, cause);
    }
}
