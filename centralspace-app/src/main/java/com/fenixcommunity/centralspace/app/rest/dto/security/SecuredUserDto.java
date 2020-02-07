package com.fenixcommunity.centralspace.app.rest.dto.security;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @Builder @FieldDefaults(level = PRIVATE)
public class SecuredUserDto {

    private final String username;
    private final String role;

    @JsonCreator
    public SecuredUserDto(@JsonProperty("username") String username,
                          @JsonProperty("log") String role) {
        this.username = username;
        this.role = role;
    }
}