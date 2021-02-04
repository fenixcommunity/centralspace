package com.fenixcommunity.centralspace.app.configuration.security.handler;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fenixcommunity.centralspace.app.service.security.helper.LoginAttemptService;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final String USERNAME = "username";

    private final LoginAttemptService loginAttemptService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AppAuthenticationFailureHandler(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException exception) throws IOException {

        //todo isBlocked implementation
        loginAttemptService.loginFailed(request.getParameter(USERNAME));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        //todo change date format
        final Map<String, Object> data = new HashMap<>();
        data.put("timestamp", Calendar.getInstance().getTime());
        data.put("exception", exception.getMessage());

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}
