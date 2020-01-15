package com.fenixcommunity.centralspace.app.service.security.user;


import java.util.Optional;

/**
 * User autosecurity operations like login and logout, and CRUD operations on {@link SecuredUser}.
 *
 * @author jerome
 */
public interface SecuredUserCrudService {

    SecuredUser save(final SecuredUser user);

    Optional<SecuredUser> find(final String id);

    Optional<SecuredUser> findByUsername(final String username);
}