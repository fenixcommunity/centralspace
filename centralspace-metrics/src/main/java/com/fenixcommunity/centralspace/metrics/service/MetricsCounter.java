package com.fenixcommunity.centralspace.metrics.service;

import static com.fenixcommunity.centralspace.metrics.service.MetricsName.FAILED_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsTags.HOST;
import static com.fenixcommunity.centralspace.metrics.service.MetricsTags.STATUS_CODE;
import static com.fenixcommunity.centralspace.metrics.service.MetricsTags.URI;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class MetricsCounter {
    @Qualifier("appMeterRegistry")
    private final MeterRegistry meterRegistry;

    void counterRestCall(final MetricsName metricsName, final Integer statusCode) {
        final List<Tag> tags = new ArrayList<>(1);
        ofNullable(statusCode).filter(id -> id > 0).ifPresent(id -> tags.add(Tag.of(STATUS_CODE.getTagName(), valueOf(id))));
        meterRegistry.counter(metricsName.name(), tags).increment(1.0);
    }

    void counterFailedRestCall(final URI uri) {
        final List<Tag> tags = new ArrayList<>(2);
        tags.add(Tag.of(HOST.getTagName(), uri.getScheme()));
        tags.add(Tag.of(URI.getTagName(), uri.toString()));
        meterRegistry.counter(FAILED_HTTP_REQUESTS.toString(), tags).increment(1.0);
    }

}
