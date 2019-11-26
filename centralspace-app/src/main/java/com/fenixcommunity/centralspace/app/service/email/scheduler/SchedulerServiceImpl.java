package com.fenixcommunity.centralspace.app.service.email.scheduler;

import com.fenixcommunity.centralspace.app.service.email.emailsender.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private EmailService emailService;


    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void mailReminder() {
        emailService.sendEmail("A","m7.kaminski@gmail.com","mail", "mail2");
    }

}
