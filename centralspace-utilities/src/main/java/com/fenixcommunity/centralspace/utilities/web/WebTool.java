package com.fenixcommunity.centralspace.utilities.web;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WebTool {
    //todo var in small functions <for(var ... , packages preferred instead multimoduling
    public static String removeLinks(final String content) {
        String result = content;
        if (isNotEmpty(content)) {
            result = result.replaceAll(",*\"_links\":\\{(.*?)}}", "");
            result = result.replaceAll(",*\"links\":\\[(.*?)]", "");
        }
        return result;
    }

    public static String extractUriPath(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String matchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
    }

    public static HttpHeaders prepareResponseHeaders(final Map<String, String> headers) {
        HttpHeaders responseHeaders = new HttpHeaders();
        headers.forEach(responseHeaders::add);
        return responseHeaders;
    }

    public static Optional<String> getPreviousPageByRequest(final HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer"));
    }

    public static URI getCurrentURI() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    }
}
