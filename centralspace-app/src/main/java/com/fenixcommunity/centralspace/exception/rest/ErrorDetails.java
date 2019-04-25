package com.fenixcommunity.centralspace.exception.rest;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
// mozna dac @Builder
class ErrorDetails {
    //    @Getter, @Setter(AccessLevel.PROTECTED)
    private ZonedDateTime timestamp;
    private String message;
    private String details;

    // builder
}
