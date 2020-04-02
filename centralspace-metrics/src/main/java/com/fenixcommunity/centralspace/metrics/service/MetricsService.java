package com.fenixcommunity.centralspace.metrics.service;

import static com.fenixcommunity.centralspace.metrics.service.MetricsName.FAILED_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.metrics.service.MetricsName.GENERAL_HTTP_REQUESTS;
import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static lombok.AccessLevel.PRIVATE;

import java.net.URI;

import com.fenixcommunity.centralspace.utilities.globalexception.NotSupportedArgumentTypeException;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import io.micrometer.core.instrument.DistributionSummary;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MetricsService {
    private final MetricsCounter metricsCounter;
    private final MetricsSummary metricsSummary;
    private final Validator validator;

    @Autowired
    public MetricsService(ValidatorFactory validatorFactory, MetricsCounter metricsCounter, MetricsSummary metricsSummary) {
        this.metricsCounter = metricsCounter;
        this.metricsSummary = metricsSummary;
        this.validator = validatorFactory.getInstance(NOT_NULL);
    }

    public void counterRestCall(final MetricsName metricsName, final Integer statusCode) {
        metricsCounter.counterRestCall(metricsName, statusCode);
    }

    public void counterFailedRestCall(final URI uri) {
        metricsCounter.counterFailedRestCall(uri);
    }

    public DistributionSummary getSummary(final MetricsName metricsName) {
        validator.isValid(metricsName);
        if (metricsName == FAILED_HTTP_REQUESTS) {
            return metricsSummary.getFailedRestCallSummary();
        } else if (metricsName == GENERAL_HTTP_REQUESTS) {
            return metricsSummary.getRestCallSummary();
        }
        throw new NotSupportedArgumentTypeException(metricsName.toString());
    }
}
