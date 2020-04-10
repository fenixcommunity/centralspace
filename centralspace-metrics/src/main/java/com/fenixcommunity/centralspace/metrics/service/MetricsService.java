package com.fenixcommunity.centralspace.metrics.service;

import static com.fenixcommunity.centralspace.metrics.service.MetricsName.FAILED_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsName.GENERAL_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsName.REST_RESOURCE_COUNTER;
import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fenixcommunity.centralspace.utilities.globalexception.NotSupportedArgumentTypeException;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.LongTaskTimer;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = PRIVATE)
public class MetricsService {
    private final MetricsCounter metricsCounter;
    private final MetricsUtils metricsUtils;
    private final MetricsSummary metricsSummary;
    private final Validator validator;
    private Map<MetricsName, Gauge> resourceCounters;

    @Autowired
    public MetricsService(MetricsCounter metricsCounter, MetricsSummary metricsSummary, MetricsUtils metricsUtils,
                          ValidatorFactory validatorFactory) {
        this.metricsCounter = metricsCounter;
        this.metricsSummary = metricsSummary;
        this.metricsUtils = metricsUtils;
        this.validator = validatorFactory.getInstance(NOT_NULL);

        this.resourceCounters = new ConcurrentHashMap<>();
    }

    public void counterRestCall(final MetricsName metricsName, final Integer statusCode) {
        metricsCounter.counterRestCall(metricsName, statusCode);
    }

    public void counterFailedRestCall(final URI uri) {
        metricsCounter.counterFailedRestCall(uri);
    }

    public String getRestSummary(final MetricsName metricsName, final String statusCode) {
        validator.isValid(metricsName);

        if (isNotEmpty(statusCode)) {
            return metricsSummary.getRestCallSummary(statusCode);
        } else if (metricsName == GENERAL_HTTP_REQUESTS) {
            return metricsSummary.getGlobalRestCallSummary();
        } else if (metricsName == FAILED_HTTP_REQUESTS) {
            return metricsSummary.getFailedRestCallSummary();
        } else if (metricsName == REST_RESOURCE_COUNTER) {
            return metricsSummary.getRestResourceCounter(getResourceCounter(REST_RESOURCE_COUNTER));
        }
        throw new NotSupportedArgumentTypeException(metricsName.name());
    }

    public LongTaskTimer getRestTimer() {
        return metricsUtils.registerRestTimer();
    }

    public Gauge registerResourceCounter(final MetricsName metricsName, final List<String> items) {
        return resourceCounters.putIfAbsent(metricsName, metricsUtils.registerResourceCounter(metricsName.name(), items));
    }

    public Gauge getResourceCounter(MetricsName metricsName) {
        return resourceCounters.get(metricsName);
    }
}
