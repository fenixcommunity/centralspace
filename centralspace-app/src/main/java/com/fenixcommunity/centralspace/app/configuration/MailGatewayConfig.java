package com.fenixcommunity.centralspace.app.configuration;


import com.fenixcommunity.centralspace.app.configuration.properties.MailProperties;
import com.fenixcommunity.centralspace.app.service.mail.scheduler.SchedulerService;
import com.fenixcommunity.centralspace.app.service.mail.scheduler.SchedulerServiceBean;
import com.fenixcommunity.centralspace.utilities.mail.properties.MailContent;
import com.fenixcommunity.centralspace.utilities.mail.properties.MailRegistrationContent;
import com.fenixcommunity.centralspace.utilities.mail.template.BasicMailMessage;
import com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate;
import com.fenixcommunity.centralspace.utilities.mail.template.RegistrationMailMessage;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

import static com.fenixcommunity.centralspace.utilities.common.Var.*;
import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.MAIL;

//todo useless? EnableScheduling EnableAsync
@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan({"com.fenixcommunity.centralspace.app.service.mail"})
@EnableConfigurationProperties(MailProperties.class)
public class MailGatewayConfig {

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private ValidatorFactory validatorFactory;
//    todo we can use also
//    @Autowired
//    private Environment env;
//    env.getRequiredProperty(EMAILGETEWAY_HOST)
//    or
//    @Value(EMAILGETEWAY_PORT)
//    int port;

    @Bean
    public SchedulerService getSchedulerService() {
        return new SchedulerServiceBean();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setProtocol(mailProperties.getProtocol());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());
// how to check connection -> telnet smtp.gmail.com 587
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.debug", "true");

        return mailSender;
    }

//    @Autowired
//    private ObjectFactory<RegistrationMailMessage> mailMessageObjectFactory;

    //todo MailContentBuilder
    @Bean("registrationMailMessage")
    public MailMessageTemplate getRegistrationMailTemplate() {
        StringBuilder textBody = new StringBuilder("This is the registration token to open your new account:\n%s\n");
        MailContent mailConfigTemplate = mailProperties.getContent();
        textBody.append(mailConfigTemplate.getDomain());
        textBody.append(LINE);
        MailRegistrationContent mailRegistrationTemplate = mailConfigTemplate.getRegistrationContent();
        textBody.append(mailRegistrationTemplate.getFullUrl());

        MailMessageTemplate mailTemplate = RegistrationMailMessage.builder()
                .from(mailConfigTemplate.getEmailFrom())
                .subject(REGISTRATION_MAIL)
                .body(textBody.toString())
                .build();

        validateMailTemplate(mailTemplate);

        return mailTemplate;
    }

    @Bean("basicMailMessage")
    public MailMessageTemplate getBasicMailTemplate() {
        StringBuilder textBody = new StringBuilder();
        MailContent mailConfigTemplate = mailProperties.getContent();
        textBody.append(mailConfigTemplate.getDomain());
//todo html body
        MailMessageTemplate mailTemplate = BasicMailMessage.builder()
                .from(mailConfigTemplate.getEmailFrom())
                .subject(BASIC_MAIL)
                .body(textBody.toString())
                .build();

        validateMailTemplate(mailTemplate);

        return mailTemplate;
    }

    private void validateMailTemplate(MailMessageTemplate template) {
        Validator validator = validatorFactory.getInstance(MAIL);
        validator.validateWithException(template);
    }
}
