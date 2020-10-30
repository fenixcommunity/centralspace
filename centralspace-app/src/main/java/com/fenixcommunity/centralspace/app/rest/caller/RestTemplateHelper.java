package com.fenixcommunity.centralspace.app.rest.caller;

import static lombok.AccessLevel.PRIVATE;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Collections;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestTemplateHelper {
    private static final String TMP_FILE_SURFIX = "tmp";
    private static final String TMP_FILE_PREFIX = "download";

    public static File getLargeFileRequest(final RestTemplate restTemplate, final URI requestedFileUrl){
        return restTemplate.execute(requestedFileUrl, HttpMethod.GET, null, clientHttpResponse -> {
            final File tempFile = File.createTempFile(TMP_FILE_PREFIX, TMP_FILE_SURFIX);
            // we can also append file content -> FileOutputStream(existentFile, true); / for example pause downloading
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(tempFile));
            return tempFile;
        });
    }

    public static HttpEntity<Object> createBasicHttpEntityWithHeaders(final MediaType mediaType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(mediaType));
        return new HttpEntity<>(headers);
    }

    public static <E> HttpEntity<E> createRestEntity(final E entity) {
        final HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(entity, headers);
    }

}
