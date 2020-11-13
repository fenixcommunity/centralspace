package com.fenixcommunity.centralspace.app.service.mail.scheduler;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailService;
import com.fenixcommunity.centralspace.utilities.common.Var;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class SchedulerServiceBean implements SchedulerService {

    private final MailService mailService;

//  @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    @Scheduled(cron = "0 15 10 16 * ?", zone = "Europe/Dublin") // 10:15 AM on the 16th day of every month in Paris time
    public void mailReminder() {
        // todo getting mails with repository
        mailService.sendBasicMail(Var.EMAIL);
    }
}
