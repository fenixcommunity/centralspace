package com.fenixcommunity.centralspace.utilities.mail.template;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegistrationMailMessage extends MailMessageTemplate {

    @Builder
    public RegistrationMailMessage(String from, String subject, String body, String replyTo, boolean htmlBody) {
        super(from, subject, body, replyTo, htmlBody);
    }
}