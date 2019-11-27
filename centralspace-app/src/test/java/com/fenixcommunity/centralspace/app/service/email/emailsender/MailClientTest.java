package com.fenixcommunity.centralspace.app.service.email.emailsender;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MailClientTest {

    private GreenMail smtpServer;

    @Autowired
    private MailClient mailClient;

    //todo fill

    @BeforeAll
    public void setUp() throws Exception {
        smtpServer = new GreenMail(new ServerSetup(25, null, "smtp"));
        smtpServer.start();
    }

    @AfterAll
    public void tearDown() throws Exception {
        smtpServer.stop();
    }

    @Test
    void sendEmail() {
    }

    @Test
    void sendMessageWithAttachment() {
    }
}