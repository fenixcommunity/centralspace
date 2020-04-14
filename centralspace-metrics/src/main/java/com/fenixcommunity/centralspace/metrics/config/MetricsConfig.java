package com.fenixcommunity.centralspace.metrics.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ComponentScan(value = {"com.fenixcommunity.centralspace.metrics"})
public class MetricsConfig {

    @Bean @Qualifier("appMeterRegistry")
    public MeterRegistry appMeterRegistry() {
        final MeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
        //todo add config
        return simpleMeterRegistry;
    }

}