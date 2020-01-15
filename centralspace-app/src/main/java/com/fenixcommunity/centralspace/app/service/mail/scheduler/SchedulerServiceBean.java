package com.fenixcommunity.centralspace.app.service.mail.scheduler;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SchedulerServiceBean implements SchedulerService {

    private final MailService mailService;

    public SchedulerServiceBean(final MailService mailService) {
        this.mailService = mailService;
    }


    //    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void mailReminder(final String to) {
        mailService.sendBasicMail(to);
    }

}
