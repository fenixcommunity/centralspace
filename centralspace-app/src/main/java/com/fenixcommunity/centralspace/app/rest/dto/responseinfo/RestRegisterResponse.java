package com.fenixcommunity.centralspace.app.rest.dto.responseinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegisterType;
import lombok.AllArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class RestRegisterResponse {
    public final String infoMessage;
    public final String redirectionLink;
    public final RegisterType registerType;

//    public URL optionalRedirectionLink;
    //todo jaka data?
}
