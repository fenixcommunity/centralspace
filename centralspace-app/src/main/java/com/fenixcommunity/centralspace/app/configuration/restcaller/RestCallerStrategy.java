package com.fenixcommunity.centralspace.app.configuration.restcaller;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Component
public class RestCallerStrategy {

    private final RestTemplateBuilder basicAuthRestTemplateBuilder;

    @Qualifier("basicAuthWebClientBuilder")
    private final WebClient.Builder basicAuthWebClientBuilder;

    public RestTemplate buildRestTemplate() {
        return basicAuthRestTemplateBuilder.build();
    }

    public WebClient buildWebClient() {
        return basicAuthWebClientBuilder.build();
    }

}
