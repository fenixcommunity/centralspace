package com.fenixcommunity.centralspace.app.service.security.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
final class SecuredUsersInMemory implements SecuredUserCrudService {

    //todo move to memory HAZELCAST or standard DB
    private final Map<String, SecuredUser> users = new HashMap<>();

    @Override
    public SecuredUser save(final SecuredUser user) {
        return users.put(user.getId(), user);
    }

    @Override
    public Optional<SecuredUser> find(final String id) {
        return ofNullable(users.get(id));
    }

    @Override
    public Optional<SecuredUser> findByUsername(final String username) {
        return users
                .values()
                .stream()
                .filter(u -> Objects.equals(username, u.getUsername()))
                .findFirst();
    }
}