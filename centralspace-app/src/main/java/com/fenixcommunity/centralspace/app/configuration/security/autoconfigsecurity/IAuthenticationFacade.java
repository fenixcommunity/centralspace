package com.fenixcommunity.centralspace.app.configuration.security.autoconfigsecurity;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}