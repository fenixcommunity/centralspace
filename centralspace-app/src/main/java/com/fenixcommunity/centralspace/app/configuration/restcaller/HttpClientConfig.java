package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static lombok.AccessLevel.PRIVATE;

import java.net.URI;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class HttpClientConfig {

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
