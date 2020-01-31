package com.fenixcommunity.centralspace.app.configuration.security.autosecurity;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.mergeLists;
import static java.util.Arrays.asList;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum SecurityRole {
    // to entity
    SWAGGER("can see and try api in swagger documentation", asList("SWAGGER")),
    DB_USER("user from database", mergeLists(asList("DB_USER"), SWAGGER.roles)),
    BASIC("basic user", asList("BASIC")),
    ADMIN("admin user", mergeLists(asList("ADMIN"), BASIC.roles, SWAGGER.roles));

    private String description;
    private List<String> roles;
}
