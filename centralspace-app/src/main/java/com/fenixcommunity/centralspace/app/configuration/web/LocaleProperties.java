package com.fenixcommunity.centralspace.app.configuration.web;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "user.locale")
@Getter @FieldDefaults(level = PRIVATE)
public class LocaleProperties {
    private String language;
    private String region;

    @ConstructorBinding
    public LocaleProperties(String language, String region) {
        this.language = language;
        this.region = region;
    }
}