package com.fenixcommunity.centralspace.utilities.globalexception;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.codehaus.plexus.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceLoadingException extends RuntimeException {
    public static final String NO_FULL_STACK_TRACE = "no full stack trace";
    private String rootCause;
    private String fullStackTrace;

    public ResourceLoadingException(String message) {
        super(message);
        fullStackTrace = NO_FULL_STACK_TRACE;
        rootCause = message;
    }

    public ResourceLoadingException(String message, Throwable throwable) {
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
