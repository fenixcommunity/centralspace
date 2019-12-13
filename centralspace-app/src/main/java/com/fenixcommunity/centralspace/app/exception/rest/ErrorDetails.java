package com.fenixcommunity.centralspace.app.exception.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
public class ErrorDetails {

    private List<String> collectedErrors;
    private ZonedDateTime timestamp;
    @ApiModelProperty(notes = "Debug information (e.g., stack trace), not visible if runtime environment is 'production'", required = false)
    private String message;
    private String details;
    private String logRef;

    public static String toStringModel() {
        return "Model:" +
                "\nErrorDetails {" +
                "\ncollectedErrors: List(String)" +
                "\ntimestamp: String" +
                "\nmessage: String" +
                "\ndetails: String" +
                "\nlogRef: random String" +
                "\n}";
    }
}
