package com.fenixcommunity.centralspace.app.configuration.security.advancedconfigsecurity;


import com.fenixcommunity.centralspace.app.configuration.security.advancedconfigsecurity.jwt.TokenService;
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
final class TokenAuthenticationService implements UserAuthenticationService {

    @NonNull
    TokenService tokens;

    @NonNull
    UserCrudService users;

    //OLD
//    @Override
//    public Optional<String> login(final String username, final String password) {
//        final String uuid = UUID.randomUUID().toString();
//        final User user = User
//                .builder()
//                .id(uuid)
//                .username(username)
//                .password(password)
//                .build();
//
//        users.save(user);
//        return Optional.of(uuid);
//    }

    @Override
    public Optional<String> login(final String username, final String password) {
        return users
                .findByUsername(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));
    }

    //10b49b6d-2c54-417f-8907-4be3986d9b10

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public void logout(final User user) {

    }
}
