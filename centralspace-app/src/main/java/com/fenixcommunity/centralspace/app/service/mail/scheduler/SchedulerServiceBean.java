package com.fenixcommunity.centralspace.app.service.mail.scheduler;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class SchedulerServiceBean implements SchedulerService {

    private final MailService mailService;

    //    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void mailReminder(final String to) {
        mailService.sendBasicMail(to);
    }

}
