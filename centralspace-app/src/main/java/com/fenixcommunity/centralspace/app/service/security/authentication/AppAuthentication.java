package com.fenixcommunity.centralspace.app.service.security.authentication;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.fenixcommunity.centralspace.app.service.security.SecuredUser;

public interface AppAuthentication {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<String> login(String username, String password);

    void logout(HttpSession httpSession);

    Optional<SecuredUser> findByToken(String token);

}

