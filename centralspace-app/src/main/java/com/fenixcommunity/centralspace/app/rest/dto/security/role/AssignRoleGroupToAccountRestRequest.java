package com.fenixcommunity.centralspace.app.rest.dto.security.role;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@NoArgsConstructor @AllArgsConstructor
public class AssignRoleGroupToAccountRestRequest {
    @ApiModelProperty(example = "2323")
    public Long accountId;
    @ApiModelProperty(example = "[2323,4443]")
    public Set<Long> roleGroupsIds;
}
