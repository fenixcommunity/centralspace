package com.fenixcommunity.centralspace.app.rest.api.adancedsecurity;


import com.fenixcommunity.centralspace.app.rest.dto.security.RequestedUser;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.service.security.advanced.SecuredUserAuthenticationService;
import com.fenixcommunity.centralspace.app.service.security.advanced.user.SecuredUser;
import com.fenixcommunity.centralspace.app.service.security.advanced.user.SecuredUserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
    @NonNull
    SecuredUserAuthenticationService authentication;
    @NonNull
    SecuredUserCrudService users;

    @PostMapping("/register")
    String register(@RequestBody RequestedUser requestedUser) {
        String username = requestedUser.getUsername();
        String password = requestedUser.getPassword();
        String role = requestedUser.getRole();
        users.findByUsername(username).ifPresent(u -> {
            throw new ServiceFailedException(format("requested username:%s exist", u.getUsername()));
        });
        users.save(SecuredUser.builder()
                .id(username)
                .username(username)
                .password(password)
                .role(role)
                .build()
        );

        return login(requestedUser);
    }

    @PostMapping("/login")
    String login(@RequestBody RequestedUser requestedUser) {
        String username = requestedUser.getUsername();
        String password = requestedUser.getPassword();
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}
