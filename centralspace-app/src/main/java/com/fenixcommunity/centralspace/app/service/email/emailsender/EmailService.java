package com.fenixcommunity.centralspace.app.service.email.emailsender;

import com.fenixcommunity.centralspace.utilities.resourcehelper.AttachmentResource;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void sendEmail(
            String fromAddress, String toAddress, String subject, String body);

    void sendMailWithAttachment(
            String fromAddress, String toAddress, String subject, String body, AttachmentResource attachment) throws MessagingException, IOException;
}