package com.fenixcommunity.centralspace.app.service.security.advanced;

import com.fenixcommunity.centralspace.app.service.security.advanced.user.SecuredUser;

import java.util.Optional;

public interface SecuredUserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<String> login(String username, String password);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<SecuredUser> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param user the user to logout
     */
    void logout(SecuredUser user);
}

