package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailGatewayController {

    private final MailService mailService;

    public MailGatewayController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/basic")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sendBasicMail() {
        mailService.sendBasicMail();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sendMailWithAttachment() {
        mailService.sendRegistrationMailWithAttachment();
        return ResponseEntity.ok().build();
    }
}
