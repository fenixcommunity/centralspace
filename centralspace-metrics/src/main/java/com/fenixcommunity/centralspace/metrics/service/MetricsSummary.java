package com.fenixcommunity.centralspace.metrics.service;

import static com.fenixcommunity.centralspace.metrics.service.MetricsName.FAILED_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsName.GENERAL_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsTags.HOST;
import static com.fenixcommunity.centralspace.metrics.service.MetricsTags.STATUS_CODE;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class MetricsSummary {
    private final MeterRegistry meterRegistry;

    DistributionSummary getFailedRestCallSummary() {
        final Iterable<Tag> tags = asList(Tag.of(HOST.getTagName(), "localhost"));
        return meterRegistry.summary(FAILED_HTTP_REQUESTS.toString(), tags);
    }

    DistributionSummary getRestCallSummary() {
        final Iterable<Tag> tags = asList(Tag.of(STATUS_CODE.getTagName(), valueOf(200)));
        return meterRegistry.summary(GENERAL_HTTP_REQUESTS.toString(), tags);
    }
}
