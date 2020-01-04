package com.fenixcommunity.centralspace.app.service.security.user;


import java.util.Optional;

/**
 * User autosecurity operations like login and logout, and CRUD operations on {@link SecuredUser}.
 *
 * @author jerome
 */
public interface SecuredUserCrudService {

    SecuredUser save(SecuredUser user);

    Optional<SecuredUser> find(String id);

    Optional<SecuredUser> findByUsername(String username);
}