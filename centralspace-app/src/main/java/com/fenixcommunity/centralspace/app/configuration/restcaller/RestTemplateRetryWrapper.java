package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static com.fenixcommunity.centralspace.metrics.service.MetricsName.REST_RESOURCE_COUNTER;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fenixcommunity.centralspace.app.globalexception.RestCallerException;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.LongTaskTimer.Sample;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import com.fenixcommunity.centralspace.metrics.service.MetricsName;
import com.fenixcommunity.centralspace.metrics.service.MetricsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
class RestTemplateRetryWrapper extends RestTemplate {
    private final MetricsService metricsService;
    private final List<@NotBlank String> items;

    public RestTemplateRetryWrapper(final BasicAuthenticationInterceptor basicAuthenticationInterceptor, MetricsService metricsService) {
        super.getInterceptors().add(basicAuthenticationInterceptor);
        this.metricsService = metricsService;
        items = new ArrayList<>();
        this.metricsService.registerResourceCounter(REST_RESOURCE_COUNTER, items);
    }

    @Retryable(
            include = {IOException.class, ResourceAccessException.class},
            exclude = {HttpStatusCodeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public <T> ResponseEntity<T> exchange(final URI uri, final HttpMethod method, final HttpEntity<?> requestEntity, final Class<T> responseType) {
        log.debug("Trying to call [{}] with method [{}]", uri, method.name());

        final LongTaskTimer timer = metricsService.getRestTimer();
        final Sample timerId = timer.start();
        try {
            final ResponseEntity<T> responseEntity = super.exchange(uri, method, requestEntity, responseType);
            metricsService.counterRestCall(MetricsName.GENERAL_HTTP_REQUESTS, responseEntity.getStatusCodeValue());
            timerId.stop();
            if (uri.getHost().contains("localhost")) {
                items.add(uri.toASCIIString());
            }
            return responseEntity;
        } catch (Exception e) {
            log.warn("Calling [{}] with method [{}] failed with exception: {}", uri.toString(), method.name(), e.getMessage());
            metricsService.counterFailedRestCall(uri);
            timerId.stop();
            throw new RestCallerException("exchange call fail for URL: " + uri.toString());
        }
    }
}
