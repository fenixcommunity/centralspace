package metrics;

public enum MetricsName {

    GENERAL_HTTP_REQUESTS("http.client.requests.calls"),
    FAILED_HTTP_REQUESTS("http.client.requests.failed");

    private final String name;

    MetricsName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
