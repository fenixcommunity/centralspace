package com.fenixcommunity.centralspace.metrics.service;

import static com.fenixcommunity.centralspace.metrics.service.MetricsName.FAILED_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsName.GENERAL_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsTags.STATUS_CODE;
import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.StringJoiner;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.Search;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class MetricsSummary {
    private final MeterRegistry meterRegistry;

    String getGlobalRestCallSummary() {
        final Search searchResult = meterRegistry.find(GENERAL_HTTP_REQUESTS.name());
        final Collection<Counter> counters = searchResult.counters();

        final StringJoiner joiner = new StringJoiner(LINE).add("Rest Call Global Summary report:");
        counters.forEach(counter -> joiner
                .add("Count: " + counter.count())
                .add(counter.getId().toString())
        );
        return joiner.toString();
    }

    String getFailedRestCallSummary() {
        final Counter counter = meterRegistry.counter(FAILED_HTTP_REQUESTS.name());
        return new StringJoiner(LINE).add("Failed Rest Call Summary report:")
                .add("Count: " + counter.count())
                .add(counter.getId().toString())
                .toString();
    }

    String getRestCallSummary(final String statusCode) {
        final Counter counter = meterRegistry.counter(GENERAL_HTTP_REQUESTS.name(), STATUS_CODE.getTagName(), statusCode);

        return new StringJoiner(LINE).add(format("Rest Call Summary report for status {%s} code:", statusCode))
                .add("Count: " + counter.count())
                .add(counter.getId().toString())
                .toString();
    }

    String getRestResourceCounter(final Gauge gauge) {
        final Iterable<Measurement> measurement = gauge.measure();
        final StringJoiner joiner = new StringJoiner(LINE).add("Rest Resource Counter:");
        measurement.forEach(m -> joiner.add(m.toString()));
        return joiner.toString();
    }


}
