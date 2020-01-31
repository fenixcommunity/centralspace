package com.fenixcommunity.centralspace.app.rest.caller;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestTemplateHelper {

    public static HttpEntity<Object> createHttpEntityWithHeaders(final MediaType mediaType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(mediaType));
        return new HttpEntity<>(headers);
    }

}
