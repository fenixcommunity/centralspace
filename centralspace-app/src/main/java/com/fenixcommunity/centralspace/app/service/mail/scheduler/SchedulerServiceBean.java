package com.fenixcommunity.centralspace.app.service.mail.scheduler;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceBean implements SchedulerService {

    @Autowired
    private MailService mailService;


//    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void mailReminder() {
        mailService.sendMail("A","m7.kaminski@gmail.com","mail", "mail2");
    }

}
