package com.fenixcommunity.centralspace.app.service.serviceconnector.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UnsupportedRemoteServiceTypeException extends RuntimeException {

    public UnsupportedRemoteServiceTypeException(String message) {
        super(message);
    }

    public UnsupportedRemoteServiceTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
