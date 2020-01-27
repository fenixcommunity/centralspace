package com.fenixcommunity.centralspace.app.configuration.restcaller.webclient;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static lombok.AccessLevel.PRIVATE;

@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class WebClientConfig {
    //good performance - better than RestTemplate -> non-blocking
    @Bean
    @Qualifier("basicAuthWebClientBuilder")
    public WebClient.Builder basicAuthWebClientBuilder() {
        return WebClient.builder()
                .baseUrl("http://localhost:8088")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunctions
                        .basicAuthentication(BASIC.name(), PASSWORD))
                .filter(logRequest());
        //todo     ...passwordEncoder().encode(PASSWORD)
    }

    test

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("WebClient request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }
}