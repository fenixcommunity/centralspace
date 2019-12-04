package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.utilities.mail.template.BasicMailMessage;
import com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate;
import com.fenixcommunity.centralspace.utilities.mail.template.RegistrationMailMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fenixcommunity.centralspace.utilities.common.Var.*;


@Configuration
public class MailGatewayConfigTest {

    @Bean("registrationMailMessage")
    public MailMessageTemplate getRegistrationMailTemplate() {
        MailMessageTemplate mailTemplate = RegistrationMailMessage.builder()
                .from(EMAIL_FROM)
                .subject(BASIC_MAIL)
                .body(MESSAGE)
                .build();


        return mailTemplate;
    }

    @Bean("basicMailMessage")
    public MailMessageTemplate getBasicMailTemplate() {

        MailMessageTemplate mailTemplate = BasicMailMessage.builder()
                .from(EMAIL_FROM)
                .subject(BASIC_MAIL)
                .body(MESSAGE)
                .build();

        return mailTemplate;
    }
}
