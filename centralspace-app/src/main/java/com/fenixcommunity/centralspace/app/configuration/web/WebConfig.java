package com.fenixcommunity.centralspace.app.configuration.web;

import static lombok.AccessLevel.PRIVATE;

import javax.annotation.Priority;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableRedisHttpSession
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WebConfig extends AbstractHttpSessionApplicationInitializer {
    private final String appViewOrigin;

    public WebConfig(@Value("${centralspace-view.origin}") String appViewOrigin) {
        this.appViewOrigin = appViewOrigin;
    }

    @Bean
    public WebMvcConfigurer appCorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                //TODO reduce/remove in real app!
                registry.addMapping("/**")
                        .allowedOrigins(/* appViewOrigin -> we use proxy in React */ "http://localhost:9000" /* to test */ )
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
