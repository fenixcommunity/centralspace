package com.fenixcommunity.centralspace.app.rest.dto.security;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data @Builder @FieldDefaults(level = PRIVATE)
public class RequestedUserDto {

    private final String username;
    private final String password;
    private final String role;
//todo -> as AccountDto
    @JsonCreator
    RequestedUserDto(@JsonProperty("username") final String username,
                     @JsonProperty("password") final String password,
                     @JsonProperty("role") final String role) {
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.role = requireNonNull(role);
    }

}