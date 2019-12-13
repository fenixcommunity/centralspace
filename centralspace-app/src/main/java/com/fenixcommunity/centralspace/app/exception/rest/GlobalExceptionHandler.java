package com.fenixcommunity.centralspace.app.exception.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.randomString;
import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;

@ControllerAdvice
@Log4j2
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(ZonedDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .logRef(randomString())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupported
            (HttpMediaTypeNotSupportedException ex, WebRequest request) {
        String unsupported = "Unsupported content type: " + ex.getContentType();
        String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(ZonedDateTime.now())
                .message(ex.getMessage())
                .details(unsupported + LINE + supported)
                .logRef(randomString())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // request ma duzo innych opcji
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(ZonedDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .logRef(randomString())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //todo test
    @ExceptionHandler(ServiceFailedException.class)
    public ResponseEntity<?> internalServerException(ServiceFailedException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(ZonedDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .logRef(randomString())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
