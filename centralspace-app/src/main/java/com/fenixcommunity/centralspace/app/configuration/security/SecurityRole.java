package com.fenixcommunity.centralspace.app.configuration.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.*;
import static java.util.Arrays.asList;

@AllArgsConstructor
@Getter
public enum SecurityRole {
// to entity
    BASIC("basic user", asList("BASIC")),
    ADMIN("admin user", mergeLists(asList("ADMIN"), BASIC.roles)),
    SWAGGER("can see and try swagger documentation", asList("SWAGGER"));

    private String description;
    private List<String> roles;
}
