package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.service.email.emailsender.EmailService;
import com.fenixcommunity.centralspace.app.utils.email.template.MailMessageTemplate;
import com.fenixcommunity.centralspace.utilities.resourcehelper.AttachmentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/mail")
public class MailGatewayController {

    @Autowired
    private EmailService emailService;

    @Autowired
    @Qualifier("basicSimpleMailMessage")
    private MailMessageTemplate basicSimpleMailMessage;

    @Autowired
    @Qualifier("registrationSimpleMailMessage")
    private MailMessageTemplate registrationMailTemplate;

    @GetMapping("/send")
    public void sendEmail(@RequestParam(value = "message", defaultValue = "empty message") String  message) {
        emailService.sendEmail(
                "A","m7.kaminski@gmail.com","mail", message);
        AttachmentResource attachment = new AttachmentResource("attachment", MediaType.APPLICATION_PDF);
        // todo add exception
        try {
            emailService.sendMailWithAttachment(
                    "B","m7.kaminski@gmail.com","mail with attachment", message, attachment);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
