package com.fenixcommunity.centralspace.app.service.security.advanced;

import com.fenixcommunity.centralspace.app.service.security.user.SecuredUserCrudService;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InMemoryUserDetailsService implements UserDetailsService {

    private final SecuredUserCrudService securedUserService;

    @Autowired
    public InMemoryUserDetailsService(final SecuredUserCrudService securedUserService) {
        this.securedUserService = securedUserService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return securedUserService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}


