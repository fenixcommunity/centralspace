package com.fenixcommunity.centralspace.app.configuration.restcaller.webclient;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static lombok.AccessLevel.PRIVATE;

@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WebClientConfig {
    //good performance - better than RestTemplate -> non-blocking
    @Bean
    @Qualifier("basicAuthWebClientBuilder")
    public WebClient.Builder basicAuthWebClientBuilder() {
        return WebClient.builder()
                .baseUrl("http://localhost:8088")
                .defaultHeaders(header -> header.setBasicAuth(BASIC.name(), PASSWORD));
    }
}