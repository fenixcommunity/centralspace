package com.fenixcommunity.centralspace.app.rest.caller;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestTemplateHelper {

    public static HttpEntity<Object> createHttpEntityWithHeaders(final MediaType mediaType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(mediaType));
        return new HttpEntity<>(headers);
    }

}
