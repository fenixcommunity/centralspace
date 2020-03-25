package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.configuration.restcaller.resttemplate.retrywrapper.RestTemplateRetryWrapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true) @AllArgsConstructor(access = PACKAGE)
public class RestCallerStrategy {

    @Qualifier("basicAuthWebClientBuilder")
    private final WebClient.Builder basicAuthWebClientBuilder;
    private final RestTemplateBuilder basicAuthRestTemplateBuilder;
    private final RestTemplateRetryWrapper restCallerRetryWrapper;

    public WebClient getWebClient() {
        return basicAuthWebClientBuilder.build();
    }

    public RestTemplate getRestTemplate() {
        return basicAuthRestTemplateBuilder.build();
    }

    public RestTemplateRetryWrapper getRestTemplateRetryWrapper() {
        return restCallerRetryWrapper;
    }

}
