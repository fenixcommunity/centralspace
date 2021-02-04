package com.fenixcommunity.centralspace.app.service.security.authentication;


import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.service.security.SecuredUser;
import com.fenixcommunity.centralspace.app.service.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
class AppAuthenticationService implements AppAuthentication {
    private final TokenService tokens;
    private final AuthenticationManager authenticationManager;
    private final JdbcUserDetailsManager userDetailsManager;


    @Override
    public Optional<String> login(final String username, final String password) {
        final Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuthentication != null && currentAuthentication.isAuthenticated()) {
            throw new ServiceFailedException("Actually logged");
        }

        final Authentication newAuthentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        // TODO auth by auth2 token
        return Optional.of(newAuthentication.getAuthorities().toString());
    }

    @Override
    public void logout(final HttpSession httpSession) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ServiceFailedException("No security context");
        } else if (httpSession == null){
            throw new ServiceFailedException("No session context provided");
        }
        httpSession.invalidate(); // also you can remove remember me cookies
        SecurityContextHolder.clearContext();
    }

    @Override
    public Optional<SecuredUser> findByToken(final String token) {
        // TODO auth by auth2 token
        return Optional.empty();
    }


}
