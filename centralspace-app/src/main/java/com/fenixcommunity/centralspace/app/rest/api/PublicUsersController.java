package com.fenixcommunity.centralspace.app.rest.api;


import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.fenixcommunity.centralspace.app.rest.dto.security.AuthenticationInfoRestData;
import com.fenixcommunity.centralspace.app.service.security.authentication.AppAuthentication;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController @RequestMapping("/public/users")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class PublicUsersController {
    @NonNull
    private final AppAuthentication appAuthentication;

    @PostMapping("/authenticated")
    public ResponseEntity<?> isAuthenticated(@Valid @RequestBody final AuthenticationInfoRestData authenticationInfo,
                                             @ApiIgnore final Principal principal) {
        return principal != null
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(UNAUTHORIZED).build();
    }

    @PostMapping("/login-call")
    public ResponseEntity<?> login(@RequestParam(value = "username", defaultValue = "max3112") final String username,
                                        @RequestParam(value = "password", defaultValue = "password1212@oqBB") final String password) {
        final Optional<String> loginRequest = appAuthentication.login(username, password);
        return ResponseEntity.ok(loginRequest.orElseGet(() -> "Failed"));
    }

    @PostMapping("/logout-call")
    public ResponseEntity<?> logout(@ApiIgnore final HttpSession httpSession) {
        appAuthentication.logout(httpSession);
        return ResponseEntity.ok(true);
    }
}
