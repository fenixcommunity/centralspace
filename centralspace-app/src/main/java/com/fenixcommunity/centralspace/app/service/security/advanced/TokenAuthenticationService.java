package com.fenixcommunity.centralspace.app.service.security.advanced;


import com.fenixcommunity.centralspace.app.service.security.advanced.jwt.TokenService;
import com.fenixcommunity.centralspace.app.service.security.user.SecuredUser;
import com.fenixcommunity.centralspace.app.service.security.user.SecuredUserCrudService;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationService implements SecuredUserAuthenticationService {

    @NonNull
    private final TokenService tokens;

    @NonNull
    private final SecuredUserCrudService users;

    @Override
    public Optional<String> login(final String username, final String password) {
        return users
                .findByUsername(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));
    }

    //todo ImmutableMap

    @Override
    public Optional<SecuredUser> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public void logout(final SecuredUser user) {

    }
}
