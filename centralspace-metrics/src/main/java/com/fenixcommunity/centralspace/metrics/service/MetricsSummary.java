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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
class MetricsSummary {
    private final MeterRegistry meterRegistry;
    private final String serverHost;

    public MetricsSummary(final MeterRegistry meterRegistry, @Value("${server.host}") final String serverHost) {
        this.meterRegistry = meterRegistry;
        this.serverHost = serverHost;
    }

    DistributionSummary getFailedRestCallSummary() {
        final Iterable<Tag> tags = asList(Tag.of(HOST.getTagName(), serverHost));
        return meterRegistry.summary(FAILED_HTTP_REQUESTS.name(), tags);
    }

    DistributionSummary getRestCallSummary() {
        final Iterable<Tag> tags = asList(Tag.of(STATUS_CODE.getTagName(), valueOf(HttpStatus.OK.value())));
        return meterRegistry.summary(GENERAL_HTTP_REQUESTS.name(), tags);
    }
}
