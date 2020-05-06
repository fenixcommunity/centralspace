package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fenixcommunity.centralspace.app.globalexception.RestCallerException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
@Log4j2
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(final ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().is4xxClientError() || httpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(final ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().is4xxClientError()) {
            final var response = new BufferedReader(new InputStreamReader(httpResponse.getBody()))
                    .lines()
                    .collect(joining("\n"));
            log.debug("Received error message response : {}", response);
            throw new RestCallerException(response);
        } else if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new RestCallerException("is5xxServerError");
        }
    }
}