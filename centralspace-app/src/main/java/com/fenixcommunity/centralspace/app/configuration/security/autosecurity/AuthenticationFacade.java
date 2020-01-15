package com.fenixcommunity.centralspace.app.configuration.security.autosecurity;

import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}