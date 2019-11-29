package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Properties;

import static com.fenixcommunity.centralspace.utilities.common.Var.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MailClient.class,
        JavaMailSenderImpl.class,
        ResourceLoaderTool.class})
@TestPropertySource(locations = {"classpath:services.properties"})
@SpringBootTest
//todo  what is it? @ActiveProfiles("mail")
class MailClientTest {

    private GreenMail smtpServer;
    private MailClient mailClient;

    @Value("${mailgateway.port}")
    private int port;

    @Value("${mailgateway.protocol}")
    private String protocol;

    @Value("${mailgateway.username}")
    private String username;

    @Value("${mailgateway.password}")
    private String password;

    @Value("${mailgateway.usermail}")
    private String usermail;

    @BeforeEach
     void setUp() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        mailClient = new MailClient(mailSender);

        ServerSetup setup = new ServerSetup(port, null, protocol);
        smtpServer = new GreenMail(setup);
        smtpServer.setUser(usermail, username, password);
        smtpServer.start();
    }

    @AfterEach
    void tearDown() {
        smtpServer.stop();
    }

    @Test
    void shouldSendMail() throws MessagingException, IOException {
        //given
        //when
        mailClient.sendMail(EMAIL_FROM, EMAIL_TO, SUBJECT, MESSAGE);
        smtpServer.waitForIncomingEmail(5000, 1);
        //then
        Message[] messages = smtpServer.getReceivedMessages();
        assertEquals(1, messages.length);
        Message message = messages[0];
        String content = (String) message.getContent();
        String subject = message.getSubject();
        String emailFrom = message.getFrom()[0].toString();
        String replyTo = message.getReplyTo()[0].toString();

        assertTrue(content.contains(MESSAGE));
        assertEquals(SUBJECT, subject);
        assertEquals(EMAIL_FROM, emailFrom);
        assertEquals(EMAIL_REPLY_TO, replyTo);
    }

    @Test
    void shouldSeendMailWithAttachment(){
        //todo fill
        //given
        //when
        //then
    }
}