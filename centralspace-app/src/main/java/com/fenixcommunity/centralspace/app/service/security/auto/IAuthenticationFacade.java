package com.fenixcommunity.centralspace.app.service.security.auto;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}