package com.fenixcommunity.centralspace.metrics.service;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum MetricsName {
    GENERAL_HTTP_REQUESTS, FAILED_HTTP_REQUESTS
}