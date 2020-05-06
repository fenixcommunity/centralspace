package com.fenixcommunity.centralspace.app.rest.api.adancedsecurity;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.security.manual.SecuredUserAuthenticationService;
import com.fenixcommunity.centralspace.app.service.security.user.SecuredUser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/users")
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true) final class SecuredUsersController {
    @NonNull
    private final SecuredUserAuthenticationService authentication;

    @GetMapping("/current")
    public SecuredUser getCurrent(@AuthenticationPrincipal final SecuredUser user) {
        return user;
    }
//todo to DTO!

    @GetMapping("/logout")
    public boolean logout(@AuthenticationPrincipal final SecuredUser user) {
        authentication.logout(user);
        return true;
    }
}