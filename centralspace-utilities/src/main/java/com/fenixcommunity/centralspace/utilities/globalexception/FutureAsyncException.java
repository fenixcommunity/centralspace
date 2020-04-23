package com.fenixcommunity.centralspace.utilities.globalexception;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FutureAsyncException extends RuntimeException {

    public FutureAsyncException(String message) {
        super(message);
    }

    public FutureAsyncException(String message, Throwable throwable) {
        super(message, throwable);
    }
}