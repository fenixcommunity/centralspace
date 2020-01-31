package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api/authorize", produces = {MediaType.ALL_VALUE})
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LoginController {

    @PostMapping("/init")
    public String authorize() {
        return "error";
    }

    @PostMapping("/success")
    public String success() {
        return "success";
    }

    @PostMapping("/error")
    public String error() {
        return "error";
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }


}
