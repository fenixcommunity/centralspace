package com.fenixcommunity.centralspace.app.rest.filter.cache;

import lombok.extern.java.Log;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fenixcommunity.centralspace.utills.common.test.Var.COOKIE_SESSION;

@Log
public class CacheCookieApiFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        var response = (HttpServletResponse) servletResponse;
        ResponseCookiesWrapper wrappedResponse = new ResponseCookiesWrapper(response);
        wrappedResponse.addCookie(new Cookie(COOKIE_SESSION, "CacheCookieApiFilter-set"));

        chain.doFilter(servletRequest, wrappedResponse);
    }
}
