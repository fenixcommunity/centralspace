package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
import com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import static com.fenixcommunity.centralspace.utilities.common.Var.EMAIL_FROM;
import static com.fenixcommunity.centralspace.utilities.common.Var.EMAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.MESSAGE;
import static com.fenixcommunity.centralspace.utilities.common.Var.SUBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MailServiceBean.class,
        JavaMailSenderImpl.class,
        ValidatorFactory.class
})
@TestPropertySource(locations = {"classpath:mail-gateway.properties"})
@SpringBootTest
//todo  what is it? @ActiveProfiles("mail")
class MailServiceBeanTest {
    //todo want more? https://github.com/greenmail-mail-test/greenmail/tree/master/greenmail-core/src/test/java/com/icegreen/greenmail/examples

    private GreenMail smtpServer;
    private MailServiceBean mailServiceBean;

    //todo MockBean and ReflectionTestUtils.setField vs Autowired?

    @MockBean
    private ResourceProperties resourceProperties;

    @InjectMocks
    private ValidatorFactory validatorFactory;

    @MockBean(name = "basicMailMessage")
    private MailMessageTemplate basicMailMessage;

    @MockBean(name = "registrationMailMessage")
    private MailMessageTemplate registrationMailMessage;

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

        mailServiceBean = new MailServiceBean(mailSender);
        setUpTemplateEngine();
        setUpResourceProperties();
        setUpBasicMailTemplate();
        setUpRegistrationMailTemplate();
        ReflectionTestUtils.setField(mailServiceBean, "resourceProperties", resourceProperties);
        ReflectionTestUtils.setField(mailServiceBean, "validatorFactory", validatorFactory);
        ReflectionTestUtils.setField(mailServiceBean, "basicMailMessage", basicMailMessage);
        ReflectionTestUtils.setField(mailServiceBean, "registrationMailMessage", registrationMailMessage);

        ServerSetup setup = new ServerSetup(port, null, protocol);
        smtpServer = new GreenMail(setup);
        smtpServer.setUser(usermail, username, password);
        smtpServer.start();
    }

    private void setUpTemplateEngine() {
    }

    private void setUpResourceProperties() {
        when(resourceProperties.getImageUrl()).thenReturn("/img");
    }

    private void setUpBasicMailTemplate() {
        when(basicMailMessage.getFrom()).thenReturn(EMAIL_FROM);
        when(basicMailMessage.getMailBody()).thenReturn(MESSAGE);
        when(basicMailMessage.getSubject()).thenReturn(SUBJECT);
    }

    private void setUpRegistrationMailTemplate() {
        when(registrationMailMessage.getFrom()).thenReturn(EMAIL_FROM);
        when(registrationMailMessage.getMailBody()).thenReturn(MESSAGE);
        when(registrationMailMessage.getSubject()).thenReturn(SUBJECT);
    }

    @AfterEach
    void tearDown() {
        smtpServer.stop();
    }

    @Test
    void shouldSendMail() throws MessagingException, IOException {
        //given
        //when
        mailServiceBean.sendBasicMail(EMAIL);
        smtpServer.waitForIncomingEmail(5000, 1);
        //then
        Message[] messages = smtpServer.getReceivedMessages();
        assertEquals(1, messages.length);
        Message message = messages[0];
        assertTrue(message.getContentType().startsWith("multipart/mixed;"));

        final Multipart part = (Multipart) message.getContent();
        assertEquals(1, part.getCount());

        final BodyPart bodyPart = part.getBodyPart(0);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bodyPart.writeTo(bytes);
        assertNotNull(bytes);
        assertTrue(bytes.toString().contains(MESSAGE));

        // for simple mail message
//        String content = (String) message.getContent();
//        String subject = message.getSubject();
//        String emailFrom = message.getFrom()[0].toString();
//        String replyTo = message.getReplyTo()[0].toString();

//        assertTrue(content.contains(MESSAGE));
//        assertEquals(SUBJECT, subject);
//        assertEquals(EMAIL_FROM, emailFrom);
//        assertEquals(EMAIL_REPLY_TO, replyTo);
    }

    @Test
    void shouldSeendMailWithAttachment() {
        //todo fill
        //given
        //when
        //then
    }
}