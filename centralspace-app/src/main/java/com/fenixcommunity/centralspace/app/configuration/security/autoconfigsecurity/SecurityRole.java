package com.fenixcommunity.centralspace.app.configuration.security.autoconfigsecurity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.mergeLists;
import static java.util.Arrays.asList;

@AllArgsConstructor
@Getter
public enum SecurityRole {
    // to entity
    SWAGGER("can see and try api in swagger documentation", asList("SWAGGER")),
    BASIC("basic user", asList("BASIC")),
    ADMIN("admin user", mergeLists(asList("ADMIN"), BASIC.roles, SWAGGER.roles));

    private String description;
    private List<String> roles;
}
