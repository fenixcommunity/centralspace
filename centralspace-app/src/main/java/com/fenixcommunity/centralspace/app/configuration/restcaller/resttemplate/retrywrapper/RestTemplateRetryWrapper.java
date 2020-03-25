package com.fenixcommunity.centralspace.app.configuration.restcaller.resttemplate.retrywrapper;

import java.io.IOException;
import java.net.URI;

import com.fenixcommunity.centralspace.app.globalexception.RestCallerException;
import lombok.extern.log4j.Log4j2;
import metrics.Metrics;
import metrics.MetricsName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Log4j2
public class RestTemplateRetryWrapper {

    @Autowired
    private Metrics metrics;

    private final RestTemplate restTemplate;

    public RestTemplateRetryWrapper(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(
            include = {IOException.class, ResourceAccessException.class},
            exclude = {HttpStatusCodeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public <T> ResponseEntity<T> exchange(URI uri, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        log.debug("Trying to call [{}] with method [{}]", uri, method.name());
        try {
            ResponseEntity<T> responseEntity  = restTemplate.exchange(uri, method, requestEntity, responseType);
            metrics.counterRestCall(MetricsName.GENERAL_HTTP_REQUESTS, responseEntity.getStatusCodeValue());
            return responseEntity;
        } catch (Exception e){
            log.warn("Calling [{}] with method [{}] failed with exception: {}", uri.toString(), method.name(), e.getMessage());
            metrics.counterFailedRestCall(uri);
            throw new RestCallerException("exchange call fail for URL: " + uri.toString());
        }
    }
    todo
}
