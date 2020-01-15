package com.fenixcommunity.centralspace.app.rest.filter.cache;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fenixcommunity.centralspace.utilities.common.Var.COOKIE_SESSION;
import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE)
public class CacheCookieApiFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(final FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

    @Override
    public void doFilter
            (final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain)
            throws IOException, ServletException {
        var response = (HttpServletResponse) servletResponse;
        final ResponseCookiesWrapper wrappedResponse = new ResponseCookiesWrapper(response);
        wrappedResponse.addCookie(new Cookie(COOKIE_SESSION, "CacheCookieApiFilter-set"));

        chain.doFilter(servletRequest, wrappedResponse);
    }
}
