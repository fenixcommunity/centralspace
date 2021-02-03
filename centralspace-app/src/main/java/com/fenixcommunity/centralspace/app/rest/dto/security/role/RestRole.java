package com.fenixcommunity.centralspace.app.rest.dto.security.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@NoArgsConstructor @AllArgsConstructor
public class RestRole {
    @ApiModelProperty(example = "ROLE_SWAGGER")
    public String name;
    @ApiModelProperty(example = "can see and try api in swagger documentation")
    public String description;
}
