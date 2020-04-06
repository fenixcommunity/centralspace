package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.metrics.service.MetricsService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
@EnableConfigurationProperties(RestCallerProperties.class)
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class RestTemplateConfig {
    private final MetricsService metricsService;
    private final RestCallerProperties restCallerProperties;

    @Bean
    @DependsOn(value = {"appRestTemplateCustomizer"})
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(appRestTemplateCustomizer());
    }

    @Bean
    AppRestTemplateCustomizer appRestTemplateCustomizer() {
        return new AppRestTemplateCustomizer(getRestTemplateAuthentication());
    }

    @Bean
    @Lazy
    RestTemplateRetryWrapper restTemplateRetryWrapper() {
        return new RestTemplateRetryWrapper(getRestTemplateAuthentication(), metricsService);
    }

    private BasicAuthenticationInterceptor getRestTemplateAuthentication() {
        return new BasicAuthenticationInterceptor(restCallerProperties.getUsername(), restCallerProperties.getPassword());
    }

}