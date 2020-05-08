package com.fenixcommunity.centralspace.app.configuration.web;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WebConfig {
    private final String appViewOrigin;

    public WebConfig(@Value("centralspace-view.origin") String appViewOrigin) {
        this.appViewOrigin = appViewOrigin;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins(appViewOrigin);
                registry.addMapping("/api/cross/**").allowedOrigins("http://localhost:9000");
            }
        };
    }
}
