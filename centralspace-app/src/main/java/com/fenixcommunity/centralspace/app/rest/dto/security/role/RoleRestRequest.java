package com.fenixcommunity.centralspace.app.rest.dto.security.role;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@NoArgsConstructor @AllArgsConstructor
public class RoleRestRequest {
    public List<RestRole> roles;
    public RestRoleGroup roleGroup;
}
