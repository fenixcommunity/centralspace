package com.fenixcommunity.centralspace.metrics.service.analyzer;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class AutoServiceLoader<T> {

    public MetadataInformation getMetadataInformation(final Class<T> className) {
        final ServiceLoader<T> loader = ServiceLoader.load(className);
        long serviceRepresentation = StreamSupport.stream(loader.spliterator(), false).count();

        return MetadataInformation.builder()
                .serviceRepresentation(serviceRepresentation)
                .build();
    }
}
