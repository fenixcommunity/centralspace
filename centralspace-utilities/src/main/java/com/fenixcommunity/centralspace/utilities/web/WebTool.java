package com.fenixcommunity.centralspace.utilities.web;

import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class WebTool {

    public static String removeLinks(String content) {
        String result = content;
        if (isNotEmpty(content)) {
            result = result.replaceAll(",*\"_links\":\\{(.*?)}}", "");
            result = result.replaceAll(",*\"links\":\\[(.*?)]", "");
        }
        return result;
    }

    public static String extractUriPath(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String matchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
    }

    public static HttpHeaders prepareResponseHeaders(Map<String, String> headers) {
        HttpHeaders responseHeaders = new HttpHeaders();
        headers.forEach(responseHeaders::add);
        return responseHeaders;
    }
}
