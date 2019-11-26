package com.fenixcommunity.centralspace.app.service.email.emailsender;

import com.fenixcommunity.centralspace.utilities.resourcehelper.AttachmentResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

//TODO CZY WSZEDZIE IMPL?
@Service
public class EmailServiceImpl implements EmailService {

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

        mailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(
            String fromAddress, String toAddress, String subject, String body, AttachmentResource attachment) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        message.setSubject(subject);
        message.setText(body);
        //todo object wrapper AttachmentResource
        Resource resource = resourceLoaderTool.loadResourceByName(attachment.getFileName(), attachment.getFileType());
        if (resource.exists()) {
            File file = resource.getFile();
            helper.addAttachment("Attachment", file);
        }

        mailSender.send(message);

    }
}
