package com.fenixcommunity.centralspace.app.rest.dto.security.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@NoArgsConstructor @AllArgsConstructor
public class RestRoleGroup {
    @ApiModelProperty(example = "ADMIN")
    public String name;
    @ApiModelProperty(example = "highest role group")
    public String description;
}
