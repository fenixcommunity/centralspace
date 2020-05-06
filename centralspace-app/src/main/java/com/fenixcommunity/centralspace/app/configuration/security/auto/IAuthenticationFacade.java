package com.fenixcommunity.centralspace.app.configuration.security.auto;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}