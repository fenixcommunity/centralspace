package com.fenixcommunity.centralspace.app.rest.dto.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class RequestedUser {

    String username;
    String password;
    String role;

    @JsonCreator
    RequestedUser(@JsonProperty("username") final String username,
                  @JsonProperty("password") final String password,
                  @JsonProperty("role") final String role) {
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.role = requireNonNull(role);
    }

}