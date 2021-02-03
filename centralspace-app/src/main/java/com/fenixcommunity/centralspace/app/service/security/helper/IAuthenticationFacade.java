package com.fenixcommunity.centralspace.app.service.security.helper;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}