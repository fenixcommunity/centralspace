package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PRIVATE;

import java.net.URI;

import com.fenixcommunity.centralspace.metrics.service.MetricsService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
@EnableConfigurationProperties(RestCallerProperties.class)
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class RestTemplateConfig {
    private final MetricsService metricsService;
    private final RestCallerProperties restCallerProperties;

    @Bean
    @Lazy
    RestTemplateRetryWrapper restTemplateRetryWrapper() {
        return new RestTemplateRetryWrapper(getRestTemplateAuthentication(), metricsService);
    }

    @Bean
    @DependsOn(value = {"appRestTemplateCustomizer", "httpComponentsClientHttpRequestFactory"})
    RestTemplateBuilder restTemplateBuilder(HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory, RestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder(appRestTemplateCustomizer())
                .requestFactory(() -> httpComponentsClientHttpRequestFactory)
                .errorHandler(errorHandler);
    }

    @Bean
    AppRestTemplateCustomizer appRestTemplateCustomizer() {
        return new AppRestTemplateCustomizer(getRestTemplateAuthentication());
    }

    private BasicAuthenticationInterceptor getRestTemplateAuthentication() {
        return new BasicAuthenticationInterceptor(restCallerProperties.getUsername(), restCallerProperties.getPassword());
    }

    // ~~~~~~~~~~~~~~~~~~~~ RequestConfigs ~~~~~~~~~~~~~~~~~~~~
    @Bean
    @Primary
    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }

    @Bean
    @Primary
    CloseableHttpClient defaultHttpClient(final PoolingHttpClientConnectionManager connectionManager, final RequestConfig requestConfig) {
        return HttpClientBuilder.create()
                .disableCookieManagement()
                .disableAuthCaching()
                .setConnectionManager(connectionManager)
                .setRedirectStrategy(new FullRedirect())
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    @Bean
    PoolingHttpClientConnectionManager defaultConnectionManager() {
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(10);
        connectionManager.setMaxTotal(50);
        return connectionManager;
    }

    @Bean
    RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(60000)
                .setConnectTimeout(60000)
                .setSocketTimeout(60000)
                .build();
    }

    private static class FullRedirect extends LaxRedirectStrategy {
        @Override
        public HttpUriRequest getRedirect(
                final HttpRequest request,
                final HttpResponse response,
                final HttpContext context) throws ProtocolException {
            final URI uri = getLocationURI(request, response, context);
            final int status = response.getStatusLine().getStatusCode();
            return status == HttpStatus.SC_TEMPORARY_REDIRECT || status == HttpStatus.SC_MOVED_TEMPORARILY
                    ? RequestBuilder.copy(request).setUri(uri).build()
                    : new HttpGet(uri);
        }
    }

}