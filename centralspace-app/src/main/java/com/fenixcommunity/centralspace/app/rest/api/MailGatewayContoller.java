package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.service.email.emailsender.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailGatewayContoller {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public void sendEmail(@RequestParam(value = "message", defaultValue = "empty message") String  message) {
        emailService.sendEmail("A","m7.kaminski@gmail.com","mail", message);
    }
}
