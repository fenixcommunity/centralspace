package com.fenixcommunity.centralspace.app.rest.filter;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class SessionFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain)
            throws IOException, ServletException {
//    TEST CASE:
//    the same as:
//    server.servlet.session.cookie.http-only=true
//    server.servlet.session.cookie.secure=true

//        final var request = (HttpServletRequest) servletRequest;
//        final var response = (HttpServletResponse) servletResponse;
//        final var allCookies = ((HttpServletRequest) servletRequest).getCookies();
//        if (allCookies != null) {
//            final Cookie session = Arrays.stream(allCookies)
//                            .filter(x -> x.getName().equals(COOKIE_SESSION_ID))
//                            .findFirst().orElse(null);
//
//            if (session != null) {
//                session.setHttpOnly(true);
//                session.setSecure(true);
//                response.addCookie(session);
//            }
//        }

        chain.doFilter(servletRequest, servletResponse);
    }
}