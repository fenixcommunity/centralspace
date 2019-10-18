package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.domain.model.password.Password;
import com.fenixcommunity.centralspace.domain.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/password")
public class PasswordController {

    private PasswordRepository passwordRepository;

    @Autowired
    public PasswordController(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @RequestMapping("/passwordexample")
    public String writeParameter(@RequestParam(value = "parameter") String parameter) {
        Password password = Password.builder()
                .password(parameter)
                .build();
        passwordRepository.save(password);
        //TODO statusy do enum lub obiektu import static javax.ws.rs.core.Response.Status.ACCEPTED, BAD_REQUEST;
        return "success";
    }

    @GetMapping("/passwords")
    public List<Password> getAllPasswords() {
        return passwordRepository.findAll();
    }
}
