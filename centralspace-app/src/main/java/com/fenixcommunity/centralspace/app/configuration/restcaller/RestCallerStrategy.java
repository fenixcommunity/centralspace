package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

    // we have also HttpClient (java 9 improvements)

    public WebClient getWebClient() {
        return basicAuthWebClientBuilder.build();
    }

    public RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = basicAuthRestTemplateBuilder.build();
        prepareGlobalMappingConverter(restTemplate);
        return restTemplate;
    }

    public RestTemplate getRetryRestTemplate() {
        final RestTemplate restTemplate = restCallerRetryWrapper;
        prepareGlobalMappingConverter(restTemplate);
        return restTemplate;
    }

    private void prepareGlobalMappingConverter(final RestTemplate restTemplate) {
        final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
    }
}
