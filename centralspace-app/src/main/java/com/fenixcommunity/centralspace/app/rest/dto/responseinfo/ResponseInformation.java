package com.fenixcommunity.centralspace.app.rest.dto.responseinfo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URL;

// JsonInclude - JAVA a,b=null,c to REST a,c
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInformation {
    public String infoMessage;
    public URL optionalRedirectionLink;
    //todo jaka data?
}
