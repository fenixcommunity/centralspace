package com.fenixcommunity.centralspace.app.rest.filter.cache;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.ArrayList;
import java.util.List;

class ResponseCookiesWrapper extends HttpServletResponseWrapper {

    private List<Cookie> cookies;

    public ResponseCookiesWrapper(HttpServletResponse response) {
        super(response);
        cookies = new ArrayList<>();
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    @Override
    public void addCookie(Cookie cookie) {
        cookies.add(cookie);

        var response = (HttpServletResponse) this.getResponse();
        response.addCookie(cookie);
    }
}