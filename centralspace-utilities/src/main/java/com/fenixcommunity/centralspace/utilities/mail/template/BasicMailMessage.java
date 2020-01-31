package com.fenixcommunity.centralspace.utilities.mail.template;

import static com.fenixcommunity.centralspace.utilities.common.Var.EMPTY;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.fenixcommunity.centralspace.utilities.mail.properties.MailContent;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.MailPreparationException;

@Getter @FieldDefaults(level = PRIVATE)
public class BasicMailMessage extends MailMessageTemplate {

    private MailContent mailContent;

    @Builder
    public BasicMailMessage(final String from, final String subject, final String replyTo, boolean htmlBody) {
        super(from, subject, replyTo, htmlBody);
    }

    @Override
    public void buildMailBodyFromProperties(Object obj) {
        if (obj instanceof MailContent && this.mailContent == null) {
            this.mailContent = (MailContent) obj;
        }
    }

    @Override
    public String getMailBody() {
        final String mailBody;
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