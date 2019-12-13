package com.fenixcommunity.centralspace.app.exception.rest;

import io.swagger.annotations.ApiModelProperty;

public class ErrorInfo {

    private String url;

    @ApiModelProperty(notes = "HTTP Status Code")
    private int statusCode;

    @ApiModelProperty(notes = "HTTP Reason Phrase")
    private String reasonPhrase;

    @ApiModelProperty(notes = "Mensage to the user")
    private String message;

    @ApiModelProperty(notes = "Ticket created on IT help desk if applicable", required = false)
    private String helpDeskTicket;

    @ApiModelProperty(notes = "Debug information (e.g., stack trace), not visible if runtime environment is 'production'", required = false)
    private String debugInfo;

    public ErrorInfo() {
    }
}
