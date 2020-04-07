package com.fenixcommunity.centralspace.app.rest.caller;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestTemplateHelper {

    public static HttpEntity<Object> createBasicHttpEntityWithHeaders(final MediaType mediaType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(mediaType));
        return new HttpEntity<>(headers);
    }

    public static <E> HttpEntity<E> createRestEntity(E entity) {
        final HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(entity, headers);
    }

}
