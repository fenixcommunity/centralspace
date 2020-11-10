package com.fenixcommunity.centralspace.app.service.mail.scheduler;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import com.fenixcommunity.centralspace.app.configuration.async.AsyncConfig;
import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AsyncConfig.class)
class SchedulerServiceBeanTest {

    @SpyBean
    private SchedulerServiceBean schedulerServiceBean;

    @MockBean
    private MailService mailService;

    @Test
    void mailReminderTest() {
        await()
                .given().ignoreException(IllegalArgumentException.class)
                .atLeast(Duration.ofSeconds(10))
                .atMost(Duration.ofSeconds(11))
                .untilAsserted(() -> verify(schedulerServiceBean, atLeast(10)).mailReminder());
    }
}