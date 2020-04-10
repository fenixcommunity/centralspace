package com.fenixcommunity.centralspace.metrics.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ComponentScan(value = {"com.fenixcommunity.centralspace.metrics"})
public class MetricsConfig {

    @Bean
    @DependsOn("meterRegistry")
    public CompositeMeterRegistry meterRegistryGlobal(final MeterRegistry meterRegistry) {
        final CompositeMeterRegistry compositeRegistry = new CompositeMeterRegistry();
        compositeRegistry.add(meterRegistry);
        return compositeRegistry;
    }

    @Bean
    public MeterRegistry meterRegistry() {
        final MeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
        //todo add config
        return simpleMeterRegistry;
    }

}