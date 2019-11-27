package com.fenixcommunity.centralspace.app.service.email.emailsender;

import com.fenixcommunity.centralspace.utilities.resourcehelper.AttachmentResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;

//TODO CZY WSZEDZIE IMPL?
@Service
public class MailClient implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoaderTool resourceLoaderTool;

    @Override
    public void sendEmail(
            String fromAddress, String toAddress, String subject, String body) {
        // it is also more complicated mail services
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            //todo handle it
        }
    }

    @Override
    public void sendMessageWithAttachment(
            String fromAddress, String toAddress, String subject, String body, AttachmentResource attachment) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom(fromAddress);

            Resource resource = resourceLoaderTool.loadResourceByName(attachment.getFileName(), attachment.getFileType());
            if (resource.exists()) {
                File file = resource.getFile();
                helper.addAttachment("Attachment", file);
            }
        };

        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            //todo handle it x2
        }
    }
}
