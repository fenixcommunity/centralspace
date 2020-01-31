package com.fenixcommunity.centralspace.app.rest.dto.responseinfo;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegisterType;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value @Builder @FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestRegisterResponse {
    private final String infoMessage;
    private final String redirectionLink;
    private final RegisterType registerType;
}
