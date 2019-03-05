package com.fenixcommunity.centralspace.Exception;

import lombok.Value;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

@Value
// mozna dac @Builder
class ErrorDetails {
    //    @Getter, @Setter(AccessLevel.PROTECTED)
    private ZonedDateTime timestamp;
    private String message;
    private String details;

    // builder
}
