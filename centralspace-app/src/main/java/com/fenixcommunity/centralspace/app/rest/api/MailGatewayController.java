package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.concurrent.TimeUnit;

import static com.fenixcommunity.centralspace.utilities.web.WebTool.prepareResponseHeaders;
import static java.util.Collections.singletonMap;

@RestController
@RequestMapping("/api/mail")
public class MailGatewayController {
    //todo account/mail/
    private final MailService mailService;

    public MailGatewayController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/basic/{to}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sendBasicMail(@PathVariable("to") @NotBlank String to) {
        mailService.sendBasicMail(to);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .headers(prepareResponseHeaders(singletonMap("Custom-Header", "mailId")))
                .build();
    }

    @PostMapping("/registration/{to}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sendMailWithAttachment(@PathVariable("to") @NotBlank String to) {
        mailService.sendRegistrationMailWithAttachment(to);
        return ResponseEntity.ok().build();
    }
}
