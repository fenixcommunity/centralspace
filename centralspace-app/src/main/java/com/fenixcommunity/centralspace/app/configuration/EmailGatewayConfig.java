package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.app.service.emailsender.EmailProperties;
import com.fenixcommunity.centralspace.app.service.emailsender.EmailService;
import com.fenixcommunity.centralspace.app.service.emailsender.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@Configuration
@EnableScheduling
@EnableAsync
public class EmailGatewayConfig {

    @Autowired
    private EmailProperties emailProperties;
//    todo we can use also
//    @Autowired
//    private Environment env;
//    env.getRequiredProperty(EMAILGETEWAY_HOST)
//    or
//    @Value(EMAILGETEWAY_PORT)
//    int port;

    @Autowired
    private EmailService emailService;

    @Bean
    public SchedulerService getAdvService() {
        return new SchedulerService();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
