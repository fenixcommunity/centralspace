package com.fenixcommunity.centralspace.metrics.config;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.fenixcommunity.centralspace.metrics.config.exception.PrometheusMeterRegistryException;
import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.PrometheusRenameFilter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ComponentScan(value = {"com.fenixcommunity.centralspace.metrics"})
public class MetricsConfig {
    //todo integrate Prometheus with Grafana by Docker -> https://technology.amis.nl/2018/11/01/monitoring-spring-boot-applications-with-prometheus-and-grafana/

    private final int prometheusPort;
    private final String prometheusEndpoint;

    public MetricsConfig(@Value("${prometheus.port}") int prometheusPort,
                         @Value("${prometheus.endpoint}") String prometheusEndpoint) {
        this.prometheusPort = prometheusPort;
        this.prometheusEndpoint = prometheusEndpoint;
    }

    @Bean
    @Qualifier("appMeterRegistry")
    public MeterRegistry appMeterRegistry() {
        final MeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
        //todo add config
        return simpleMeterRegistry;
    }

    @Bean
    PrometheusMeterRegistry prometheusMeterRegistry() {
        PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(prometheusPort), 0);
            server.createContext(prometheusEndpoint, httpExchange -> {
                String response = prometheusRegistry.scrape();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });

            new Thread(server::start).start();
        } catch (IOException e) {
            throw new PrometheusMeterRegistryException(e);
        }

        prometheusRegistry.config().meterFilter(new PrometheusRenameFilter());

        return prometheusRegistry;
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "Centralspace App");
    }

}