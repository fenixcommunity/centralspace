package com.fenixcommunity.centralspace.app.rest.dto.responseinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegisterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
// basic json response annotations
@Getter @Setter @Builder
public class RestRegisterResponse {
    public String infoMessage;
    public String redirectionLink;
    public RegisterType registerType;
}
