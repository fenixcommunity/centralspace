package com.fenixcommunity.centralspace.app.service.security.authentication;


import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import com.fenixcommunity.centralspace.app.service.security.SecuredUser;
import com.fenixcommunity.centralspace.app.service.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
class AppAuthenticationService implements AppAuthentication {
    @NonNull
    private final TokenService tokens;

    @Override
    public Optional<String> login(final String username, final String password) {
        //TODO
        return Optional.empty();
    }

    @Override
    public Optional<SecuredUser> findByToken(final String token) {
        //TODO
        return Optional.empty();
    }

    @Override
    public void logout(final SecuredUser user) {
        //TODO
    }
}
