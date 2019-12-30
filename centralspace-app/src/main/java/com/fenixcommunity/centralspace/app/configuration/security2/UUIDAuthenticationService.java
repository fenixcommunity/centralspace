package com.fenixcommunity.centralspace.app.configuration.security2;


import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service

@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {

    @NonNull
    UserCrudService users;

    FilterRegistrationBean filterRegistrationBean;

    UUIDAuthenticationService(@NonNull UserCrudService users, FilterRegistrationBean filterRegistrationBean) {
        this.users = users;
        this.filterRegistrationBean = filterRegistrationBean.getFilter();
    }

    @Override
    public Optional<String> login(final String username, final String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = User
                .builder()
                .id(uuid)
                .username(username)
                .password(password)
                .build();

        users.save(user);
        return Optional.of(uuid);
    }

    //10b49b6d-2c54-417f-8907-4be3986d9b10
    @Override
    public Optional<User> findByToken(final String token) {
        return users.find(token);
    }

    @Override
    public void logout(final User user) {

    }
}
