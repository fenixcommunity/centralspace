package com.fenixcommunity.centralspace.app.configuration.security.advancedconfigsecurity;


import java.util.Optional;

/**
 * User autoconfigsecurity operations like login and logout, and CRUD operations on {@link User}.
 *
 * @author jerome
 */
public interface UserCrudService {

    User save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);
}