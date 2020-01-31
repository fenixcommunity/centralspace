package com.fenixcommunity.centralspace.app.configuration.security.advancedsecurity;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PACKAGE;
import static org.apache.commons.lang3.StringUtils.removeStart;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@FieldDefaults(level = PACKAGE, makeFinal = true) final class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String BEARER = "Bearer";

    //look also: FilterChainProxy contains:
//    context.request.async.WebAsyncManagerIntegrationFilter
//    context.SecurityContextPersistenceFilter
//    header.HeaderWriterFilter
//    authentication.logout.LogoutFilter
//    authentication.UsernamePasswordAuthenticationFilter
    TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response) {
        final String param = ofNullable(request.getHeader(AUTHORIZATION))
                .orElse(request.getParameter("t"));

        final String token = ofNullable(param)
                .map(value -> removeStart(value, BEARER))
                .map(String::trim)
                .orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));

        final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//    }

}