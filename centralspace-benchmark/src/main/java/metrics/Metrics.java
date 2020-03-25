package metrics;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static metrics.MetricsName.FAILED_HTTP_REQUESTS;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Metrics {

    private static final String HOST_TAG = "host";
    private static final String URI_TAG = "uri";
    private static final String STATUS_CODE_TAG = "statusCode";

    @Autowired
    private MeterRegistry meterRegistry;

    public void counterRestCall(final MetricsName metricsName, final Integer statusCode) {
        final List<Tag> tags = new ArrayList<>(1);
        ofNullable(statusCode).filter(id -> id > 0).ifPresent(id -> tags.add(Tag.of(STATUS_CODE_TAG, valueOf(id))));
        meterRegistry.counter(metricsName.toString(), tags).increment();
    }

    public void counterFailedRestCall(final URI uri) {
        final List<Tag> tags = new ArrayList<>(2);
        tags.add(Tag.of(HOST_TAG, uri.getHost()));
        tags.add(Tag.of(URI_TAG, uri.getPath()));
        meterRegistry.counter(FAILED_HTTP_REQUESTS.toString(), tags).increment();
    }

    public DistributionSummary getFailedRestCallSummary() {
        final Iterable<Tag> tags = asList(Tag.of(HOST_TAG, "localhost"));
        return meterRegistry.summary(FAILED_HTTP_REQUESTS.toString(), tags);
    }

    public DistributionSummary getRestCallSummary() {
        final Iterable<Tag> tags = asList(Tag.of(STATUS_CODE_TAG, valueOf(200)));
        return meterRegistry.summary(FAILED_HTTP_REQUESTS.toString(), tags);
    }

    controller
}
