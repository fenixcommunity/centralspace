package com.fenixcommunity.centralspace.app.rest.dto.register;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@NoArgsConstructor @AllArgsConstructor @Builder
public class RestContactDetails {
    @NotNull
    @ApiModelProperty(example = "Dublin")
    public String country;

    @NotNull
    @ApiModelProperty(example = "333 444 555")
    public String phoneNumber;
}