package com.fenixcommunity.centralspace.app.configuration.restcaller.resttemplate;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class AppRestTemplateCustomizer implements RestTemplateCustomizer {
    private final String username;
    private final String password;

    @Override
    public void customize(final RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new AppClientHttpRequestInterceptor());
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
    }
}