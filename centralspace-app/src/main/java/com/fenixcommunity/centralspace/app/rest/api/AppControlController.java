package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.helper.AppControlService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api/app-control")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AppControlController {

    private final AppControlService appControlService;

    @PostMapping("/restart-app")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> restartApp() {
        appControlService.restartApp();
        return ResponseEntity.ok().body("Done");
    }

    @PostMapping("/refresh-app")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> refreshApp() {
        appControlService.refreshApp();
        return ResponseEntity.ok().body("Done");
    }
}
