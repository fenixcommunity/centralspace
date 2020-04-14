package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(RestCallerProperties.class)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
class WebClientConfig {
    //good performance - better than RestTemplate -> non-blocking

    private final RestCallerProperties restCallerProperties;
    private final String serverHost;

    WebClientConfig(final RestCallerProperties restCallerProperties, @Value("${server.host}") String serverHost) {
        this.restCallerProperties = restCallerProperties;
        this.serverHost = serverHost;
    }

    @Bean @Qualifier("basicAuthWebClientBuilder")
    WebClient.Builder basicAuthWebClientBuilder() {
        return WebClient.builder()
                .baseUrl(serverHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunctions
                        .basicAuthentication(restCallerProperties.getUsername(), restCallerProperties.getPassword()))
                .filter(logRequest());
        //todo     ...passwordEncoder().encode(PASSWORD)
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("WebClient request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }
}