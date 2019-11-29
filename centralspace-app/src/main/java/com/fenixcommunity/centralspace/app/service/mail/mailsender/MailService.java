package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import com.fenixcommunity.centralspace.utilities.resourcehelper.AttachmentResource;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {

    void sendMail(
            String fromAddress, String toAddress, String subject, String body);

    void sendMailWithAttachment(
            String fromAddress, String toAddress, String subject, String body, AttachmentResource attachment) throws MessagingException, IOException;
}