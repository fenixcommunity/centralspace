package com.fenixcommunity.centralspace.app.rest.dto.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RequestedUser {

    private final String username;
    private final String password;
    private final String role;

    @JsonCreator
    RequestedUser(@JsonProperty("username") final String username,
                  @JsonProperty("password") final String password,
                  @JsonProperty("role") final String role) {
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.role = requireNonNull(role);
    }

}