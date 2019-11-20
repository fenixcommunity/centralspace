package com.fenixcommunity.centralspace.app.service.scheduler;

import com.fenixcommunity.centralspace.app.service.emailsender.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private EmailService emailService;

        public void mailReminder() {
        }
//    @Scheduled(fixedDelay = 250 * 1000)
//    public void mailReminder() {
//        emailService.sendEmail("A","m7.kaminski@gmail.com","mail", "mail2");
//    }
}
