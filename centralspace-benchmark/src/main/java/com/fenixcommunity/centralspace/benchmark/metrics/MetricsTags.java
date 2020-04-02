package com.fenixcommunity.centralspace.benchmark.metrics;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum MetricsTags {

    URI("uri"),
    HOST("host"),
    STATUS_CODE("statusCode");

    private final String tagName;

    MetricsTags(final String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
