package com.fenixcommunity.centralspace.app.configuration.security.autosecurity;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}