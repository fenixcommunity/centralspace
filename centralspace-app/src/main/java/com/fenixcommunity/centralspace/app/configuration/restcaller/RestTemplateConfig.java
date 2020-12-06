package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.metrics.service.MetricsService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
@EnableConfigurationProperties(RestCallerProperties.class)
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class RestTemplateConfig {
    private final MetricsService metricsService;
    private final RestCallerProperties restCallerProperties;

    @Bean
    @Lazy
    RestTemplateRetryWrapper restTemplateRetryWrapper() {
        return new RestTemplateRetryWrapper(getRestTemplateAuthentication(), metricsService);
    }

    @Bean
    @DependsOn(value = {"appRestTemplateCustomizer", "httpComponentsClientHttpRequestFactory"})
    RestTemplateBuilder restTemplateBuilder(final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory,
                                            final RestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder(appRestTemplateCustomizer())
                .requestFactory(() -> httpComponentsClientHttpRequestFactory)
                .errorHandler(errorHandler);
    }

    @Bean
    AppRestTemplateCustomizer appRestTemplateCustomizer() {
        return new AppRestTemplateCustomizer(getRestTemplateAuthentication());
    }

    private BasicAuthenticationInterceptor getRestTemplateAuthentication() {
        return new BasicAuthenticationInterceptor(restCallerProperties.getUsername(), restCallerProperties.getPassword());
    }

    @Bean
    @Primary
    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(final CloseableHttpClient httpClient) {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }

}