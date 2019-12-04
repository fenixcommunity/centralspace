package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
import com.fenixcommunity.centralspace.utilities.mail.MailBuilder;
import com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
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

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.MAIL;

//TODO CZY WSZEDZIE IMPL?
@Service
public class MailServiceBean implements MailService {

    private final JavaMailSender mailSender;
    //todo constuctor all or autowired?
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ValidatorFactory validatorFactory;

    @Autowired
    private ResourceLoaderTool resourceLoaderTool;

    @Autowired
    private ResourceProperties resourceProperties;

    @Autowired
    @Qualifier("basicMailMessage")
    private MailMessageTemplate basicMailMessage;

    @Autowired
    @Qualifier("registrationMailMessage")
    private MailMessageTemplate registrationMailMessage;

    @Autowired
    public MailServiceBean(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendBasicMail(String to) throws MailException {
        MailBuilder mailBuilder = new MailBuilder(basicMailMessage);
        mailBuilder.addTo(to);
//        mailBuilder.setHtmlBody(true);
        if (mailBuilder.isHtmlBody()) {
            mailBuilder.setBody(getBasicHtmlBody());
        }
        sendBasicMail(mailBuilder);
    }

    @Override
    public void sendRegistrationMailWithAttachment(String to) throws MailException {
        MailBuilder mailBuilder = new MailBuilder(registrationMailMessage);
        mailBuilder.addTo(to);
        InternalResource attachment = getRegistrationAttachment();
        sendMailWithAttachment(mailBuilder, attachment);
    }

    private void sendBasicMail(MailBuilder mailBuilder) throws MailException {
        validateMailContent(mailBuilder);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailBuilder.getToArray());
            helper.setSubject(mailBuilder.getSubject());
            helper.setText(mailBuilder.getBody(), mailBuilder.isHtmlBody());
            helper.setFrom(mailBuilder.getFrom());

        };

        mailSender.send(messagePreparator);
    }

    private void sendMailWithAttachment(MailBuilder mailBuilder, InternalResource attachment) throws MailException {
        validateMailContent(mailBuilder);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailBuilder.getToArray());
            helper.setSubject(mailBuilder.getSubject());
            helper.setText(mailBuilder.getBody(), mailBuilder.isHtmlBody());
            helper.setFrom(mailBuilder.getFrom());

            if (attachment.fileExists()) {
                helper.addAttachment("Attachment", attachment.getContent().getFile());
            }
        };

        mailSender.send(messagePreparator);
    }

    private InternalResource getRegistrationAttachment() {
        InternalResource attachment = InternalResource.resourceByNameAndType("attachment", MediaType.APPLICATION_PDF);
        attachment.setContent(resourceLoaderTool.loadResourceFile(attachment));
        return attachment;
    }

    private String getBasicHtmlBody() {
        Context mailTemplateContext = new Context();
        mailTemplateContext.setVariable("imageUrl", resourceProperties.getImageUrl());
        return templateEngine.process("template", mailTemplateContext);
    }

    private void validateMailContent(MailBuilder mailBuilder) {
        Validator validator = validatorFactory.getInstance(MAIL);
        validator.validateWithException(mailBuilder);
    }
}
