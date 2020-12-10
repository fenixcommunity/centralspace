package com.fenixcommunity.centralspace.app.rest.dto.utils;

import static java.util.Objects.requireNonNullElse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CrossOriginResponse {
    private String crossOriginDomain;
    private String message;

    @JsonCreator
    public CrossOriginResponse(@JsonProperty("domain") String crossOriginDomain,
                               @JsonProperty("message") String message) {
        this.crossOriginDomain = requireNonNullElse(crossOriginDomain, "localhost");
        this.message = message;
    }
}
