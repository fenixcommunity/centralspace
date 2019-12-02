package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import com.fenixcommunity.centralspace.app.utils.mail.template.MailMessageTemplate;
import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceApp;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    @Qualifier("basicMailMessage")
    private MailMessageTemplate basicMailMessage;

    @Autowired
    @Qualifier("registrationSimpleMailMessage")
    private MailMessageTemplate registrationMailTemplate;

    @Autowired
    public MailServiceBean(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendBasicMail() throws MailException {
        basicMailMessage.setText(getBasicHtmlBody());
        basicMailMessage.setHtmlBody(true);
        sendBasicMail(basicMailMessage);
    }

    @Override
    public void sendRegistrationMailWithAttachment() throws MailException {
        ResourceApp attachment = getRegistrationAttachment();
        sendMailWithAttachment(registrationMailTemplate, attachment);
    }

    private void sendBasicMail(MailMessageTemplate mailContent) throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailContent.getTo());
            helper.setSubject(mailContent.getSubject());
            helper.setText(mailContent.getText(), mailContent.isHtmlBody());
            helper.setFrom(mailContent.getFrom());
        };

        mailSender.send(messagePreparator);
    }

    private void sendMailWithAttachment(MailMessageTemplate mailContent, ResourceApp attachment) throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailContent.getTo());
            helper.setSubject(mailContent.getSubject());
            helper.setText(mailContent.getText(), mailContent.isHtmlBody());
            helper.setFrom(mailContent.getFrom());

            if (attachment.fileExists()) {
                helper.addAttachment("Attachment", attachment.getContent().getFile());
            }
        };

        mailSender.send(messagePreparator);
    }

    private ResourceApp getRegistrationAttachment() {
        ResourceApp attachment = ResourceApp.resourceByNameAndType("attachment", MediaType.APPLICATION_PDF);
        attachment.setContent(resourceLoaderTool.loadResourceFile(attachment));
        return attachment;
    }

    //todo Mail creator
    private String getBasicHtmlBody() {
        Context mailTemplateContext = new Context();
        mailTemplateContext.setVariable("imageUrl", resourceProperties.getImageUrl());
        return templateEngine.process("template", mailTemplateContext);
    }
}
