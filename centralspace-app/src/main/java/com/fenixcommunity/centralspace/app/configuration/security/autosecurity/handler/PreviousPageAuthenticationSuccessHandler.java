package com.fenixcommunity.centralspace.app.configuration.security.autosecurity.handler;

import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PreviousPageAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
            throws IOException, ServletException {
        final AuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.onAuthenticationSuccess(request, response, authentication);

    }
}