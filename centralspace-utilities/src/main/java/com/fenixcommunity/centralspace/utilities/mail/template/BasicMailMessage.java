package com.fenixcommunity.centralspace.utilities.mail.template;

import com.fenixcommunity.centralspace.utilities.mail.properties.MailContent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.mail.MailPreparationException;

import static com.fenixcommunity.centralspace.utilities.common.Var.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
public class BasicMailMessage extends MailMessageTemplate {

    private MailContent mailContent;

    @Builder
    public BasicMailMessage(String from, String subject, String replyTo, boolean htmlBody) {
        super(from, subject, replyTo, htmlBody);
    }

    @Override
    public void buildMailBodyFromProperties(Object obj) {
        if (obj instanceof MailContent && this.mailContent == null){
            this.mailContent = (MailContent) obj;
        }
    }

    @Override
    public String getMailBody() {
        String mailBody = EMPTY;
        if (mailContent == null) {
            throw new MailPreparationException("mailContent hasn't been initialized");
        }
        mailBody = mailContent.getDomain();
        return mailBody;
    }

    public boolean isTemplateReady() {
        return isNotEmpty(from) && isNotEmpty(subject) && !EMPTY.equals(getMailBody());
    }
}