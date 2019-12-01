package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

import static com.fenixcommunity.centralspace.utilities.common.Var.MESSAGE;
import static com.fenixcommunity.centralspace.utilities.common.Var.SUBJECT;

//TODO CZY WSZEDZIE IMPL?
@Service
public class MailServiceBean implements MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    private ResourceLoaderTool resourceLoaderTool;

    @Autowired(required = false)
    private ResourceProperties resourceProperties;

    @Autowired
    public MailServiceBean(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendMail(
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
    public void sendMailWithAttachment(
            String fromAddress, String toAddress, String subject, String body, AttachmentResource attachment) {

        Context mailTemplateContext = new Context();
        mailTemplateContext.setVariable("imageUrl", resourceProperties.getImageUrl());
        mailTemplateContext.setVariable("title", SUBJECT);
        mailTemplateContext.setVariable("description", MESSAGE);

        String htmlBody = templateEngine.process("template", mailTemplateContext);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.setFrom(fromAddress);

            Resource resource = resourceLoaderTool.loadResourceByNameAndType(attachment.getFileName(), attachment.getFileType());
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
