package com.fenixcommunity.centralspace.app.globalexception;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DocumentServiceException extends RuntimeException {

    public DocumentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
