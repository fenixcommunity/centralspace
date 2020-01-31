package com.fenixcommunity.centralspace.app.rest.api.adancedsecurity;


import static java.lang.String.format;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.rest.dto.security.RequestedUserDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.service.security.advanced.SecuredUserAuthenticationService;
import com.fenixcommunity.centralspace.app.service.security.user.SecuredUser;
import com.fenixcommunity.centralspace.app.service.security.user.SecuredUserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/public/users")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true) final class PublicUsersController {
    //todo to all or validateNotNull
    @NonNull
    private final SecuredUserAuthenticationService authentication;
    @NonNull
    private final SecuredUserCrudService users;

    @PostMapping("/register")
    public String register(@RequestBody final RequestedUserDto requestedUserDto) {
        final String username = requestedUserDto.getUsername();
        final String password = requestedUserDto.getPassword();
        final String role = requestedUserDto.getRole();
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

        return login(requestedUserDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody final RequestedUserDto requestedUserDto) {
        final String username = requestedUserDto.getUsername();
        final String password = requestedUserDto.getPassword();
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}
