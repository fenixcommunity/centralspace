package com.fenixcommunity.centralspace.app.configuration.security;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.mergeListsWithUniqueElem;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum SecurityUserGroup {
    GUEST_USER("guest user", mergeListsWithUniqueElem(List.of("ROLE_GUEST"))),
    BASIC_USER("basic user", mergeListsWithUniqueElem(List.of("ROLE_BASIC"))),
    SWAGGER_USER("can see and try api in swagger documentation", mergeListsWithUniqueElem(List.of("ROLE_SWAGGER"))),
    FLUX_USER_BASIC("flux api caller with right to get", mergeListsWithUniqueElem(
            List.of("ROLE_FLUX_GETTER"),
            SWAGGER_USER.authorities)),
    FLUX_USER_ADVANCE("flux api caller with right to get, post, put, delete", mergeListsWithUniqueElem(
            List.of("ROLE_FLUX_EDITOR"),
            FLUX_USER_BASIC.authorities)),
    DB_USER("database manager", mergeListsWithUniqueElem(
            List.of("ROLE_DB_MANAGE, ROLE_GRAPHQL_MANAGE, ROLE_CREATE_ACCOUNT"),
            BASIC_USER.authorities,
            SWAGGER_USER.authorities)),
    ADMIN_USER("admin user", mergeListsWithUniqueElem(
            List.of("ROLE_ADMIN", "ROLE_MANAGE_ROLE"),
            BASIC_USER.authorities,
            SWAGGER_USER.authorities,
            DB_USER.authorities));

    private final String description;
    private final List<String> authorities;

    public static Set<String> getAllSecurityUserGroupNames() {
        return Arrays.stream(SecurityUserGroup.values())
                .map(Enum::name)
                .collect(toSet());
    }

    public static Set<String> getAllAuthorities() {
        return Arrays.stream(SecurityUserGroup.values())
                .map(SecurityUserGroup::getAuthorities)
                .flatMap(Collection::stream).collect(toSet());
    }
}
