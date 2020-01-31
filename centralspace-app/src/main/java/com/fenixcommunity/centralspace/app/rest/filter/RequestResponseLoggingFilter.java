package com.fenixcommunity.centralspace.app.rest.filter;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter
            (final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain)
            throws IOException, ServletException {
        final var request = (HttpServletRequest) servletRequest;
        final var response = (HttpServletResponse) servletResponse;
        final String requestURI = request.getRequestURI();

        log.info(format("START Api call requestURI: %s", requestURI));
        chain.doFilter(request, response);
        log.info(format("END Api call requestURI: %s", requestURI));
    }
}
