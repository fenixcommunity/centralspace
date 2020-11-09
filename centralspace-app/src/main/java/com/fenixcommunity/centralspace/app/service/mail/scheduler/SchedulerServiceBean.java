package com.fenixcommunity.centralspace.app.service.mail.scheduler;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class SchedulerServiceBean implements SchedulerService {

    private final MailService mailService;

    //    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void mailReminder(@NonNull final String to) {
        mailService.sendBasicMail(to);
    }

}
