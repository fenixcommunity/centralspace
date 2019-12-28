package com.fenixcommunity.centralspace.app.configuration.restcaller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import static com.fenixcommunity.centralspace.app.configuration.security.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;

@Configuration
public class RestTemplateConfig {

    @Bean
    @DependsOn(value = {"appRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(appRestTemplateCustomizer());
    }

    //todo - to get resources inside app, in future please handle autorization for other external services
    @Bean
    public AppRestTemplateCustomizer appRestTemplateCustomizer() {
        return new AppRestTemplateCustomizer(BASIC.name(), PASSWORD);
    }
}