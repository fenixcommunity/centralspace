package com.fenixcommunity.centralspace.app.rest.exception;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.codehaus.plexus.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ServiceFailedException extends RuntimeException {
    public static final String NO_FULL_STACK_TRACE = "no full stack trace";
    private String rootCause;
    private String fullStackTrace;

    public ServiceFailedException(String message) {
        super(message);
        fullStackTrace = NO_FULL_STACK_TRACE;
        rootCause = message;
    }

    public ServiceFailedException(String message, Throwable throwable) {
        super(message, throwable);
        fullStackTrace = ExceptionUtils.getFullStackTrace(throwable);
        rootCause = ExceptionUtils.getRootCause(throwable).getMessage();
    }

    public String getRootCause() {
        return rootCause;
    }

    public String getFullStackTrace() {
        return fullStackTrace;
    }
}
