package com.fenixcommunity.centralspace.metrics.service;

import static com.fenixcommunity.centralspace.metrics.service.MetricsName.REST_TIMER;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class MetricsUtils {
    @Qualifier("appMeterRegistry")
    private final MeterRegistry meterRegistry;

    LongTaskTimer registerRestTimer() {
        return LongTaskTimer
                .builder(REST_TIMER.name())
                .register(meterRegistry);
    }

    Gauge registerResourceCounter(final String resourceName, final List<String> items) {
        return Gauge.builder(resourceName, items, List::size)
                .description("localhost request counter")
                .register(meterRegistry);
    }

}