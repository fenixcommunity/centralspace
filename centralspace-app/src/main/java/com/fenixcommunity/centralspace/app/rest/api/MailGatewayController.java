package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.web.WebTool.prepareResponseHeaders;
import static java.util.Collections.singletonMap;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotBlank;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/mail")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class MailGatewayController {
    //todo account/mail/
    private final MailService mailService;

    @PostMapping("/basic/{to}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sendBasicMail(@PathVariable("to") @NotBlank final String to) {
        mailService.sendBasicMail(to);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .headers(prepareResponseHeaders(singletonMap("Custom-Header", "mailId")))
                .build();
    }

    @PostMapping("/registration/{to}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sendMailWithAttachment(@PathVariable("to") @NotBlank final String to) {
        mailService.sendRegistrationMailWithAttachment(to);
        return ResponseEntity.ok().build();
    }
}
