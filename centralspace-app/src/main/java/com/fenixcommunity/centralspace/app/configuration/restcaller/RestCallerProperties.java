package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "restcaller.credentials") @PropertySource("classpath:security.yml")
@Getter @FieldDefaults(level = PRIVATE)
class RestCallerProperties {
    private String username;
    private String password;

    @ConstructorBinding
    RestCallerProperties(String username, String password) {
        this.username = username;
        this.password = password;
    }
}