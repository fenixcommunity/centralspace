package com.fenixcommunity.centralspace.Exception;

import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
class ErrorDetails {
    //    @Getter, @Setter(AccessLevel.PROTECTED)
    private LocalDate timestamp;
    private String message;
    private String details;

    // builder
}
