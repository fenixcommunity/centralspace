package com.fenixcommunity.centralspace.app.rest.exception;

import static com.fenixcommunity.centralspace.utilities.common.Var.anyString;
import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value @Builder @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ErrorDetails {

    @ApiModelProperty(notes = "Debug information (e.g., stack trace), not visible if runtime environment is 'production'", required = true)
    private String message;
    private String details;
    private List<String> collectedErrors;
    private String logRef = anyString(30);
    private ZonedDateTime timestamp = ZonedDateTime.now();

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
