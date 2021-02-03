package com.fenixcommunity.centralspace.app.rest.api;


import static java.lang.String.format;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.rest.dto.security.RequestedUserDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.service.security.authentication.AppAuthentication;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/public/users")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
final class PublicUsersController {
    @NonNull
    private final AppAuthentication authentication;

    //todo test it

    @PostMapping("/login")
    public String login(@RequestBody final RequestedUserDto requestedUserDto) {
        final String username = requestedUserDto.getUsername();
        final String password = requestedUserDto.getPassword();
        return authentication
                .login(username, password)
                .orElseThrow(() -> {
                    throw new ServiceFailedException(format("requested username:%s not exist", username));
                });
    }
}
