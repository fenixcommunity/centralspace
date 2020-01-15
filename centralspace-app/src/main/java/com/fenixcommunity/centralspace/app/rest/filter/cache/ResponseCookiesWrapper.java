package com.fenixcommunity.centralspace.app.rest.filter.cache;

import lombok.experimental.FieldDefaults;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class ResponseCookiesWrapper extends HttpServletResponseWrapper {

    private List<Cookie> cookies;

    public ResponseCookiesWrapper(final HttpServletResponse response) {
        super(response);
        cookies = new ArrayList<>();
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    @Override
    public void addCookie(final Cookie cookie) {
        cookies.add(cookie);

        final var response = (HttpServletResponse) this.getResponse();
        response.addCookie(cookie);
    }
}