package com.fenixcommunity.centralspace.utilities.mail.template;

import com.fenixcommunity.centralspace.utilities.mail.properties.MailRegistrationContent;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.MailPreparationException;

import static com.fenixcommunity.centralspace.utilities.mail.properties.MailRegistrationContent.ACCOUNT_TOKEN;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
public class RegistrationMailMessage extends MailMessageTemplate {

   private MailRegistrationContent mailRegistrationContent;

    @Builder
    public RegistrationMailMessage(String from, String subject, String replyTo, boolean htmlBody) {
        super(from, subject, replyTo, htmlBody);
    }

    @Override
    public void buildMailBodyFromProperties(Object obj) {
        if (obj instanceof MailRegistrationContent && this.mailRegistrationContent == null){
            this.mailRegistrationContent = (MailRegistrationContent) obj;
        }
    }

    @Override
    public String getMailBody() {
        String mailBody = "This is the registration token to open your new account:\n\n";
        if (mailRegistrationContent == null) {
            throw new MailPreparationException("mailRegistrationContent hasn't been initialized");
        }
        mailBody = mailBody + mailRegistrationContent.getFullUrl().
                replace(MailRegistrationContent.ACCOUNT_TOKEN,
                        RandomStringUtils.random(64, true, true));
        return mailBody;
    }

    public boolean isTemplateReady() {
        return isNotEmpty(from) && isNotEmpty(subject)
                && mailRegistrationContent != null && mailRegistrationContent.getFullUrl().contains(ACCOUNT_TOKEN);
    }
}