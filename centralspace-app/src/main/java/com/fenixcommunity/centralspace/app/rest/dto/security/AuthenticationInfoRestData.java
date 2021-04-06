package com.fenixcommunity.centralspace.app.rest.dto.security;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuthenticationInfoRestData {
    @NotNull
    @JsonProperty
    @ApiModelProperty(example = "/beautypage")
    public String locationPath;
}
