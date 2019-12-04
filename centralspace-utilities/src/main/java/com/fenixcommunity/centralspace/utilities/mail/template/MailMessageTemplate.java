package com.fenixcommunity.centralspace.utilities.mail.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

//TODO 27.11.2019 what about getters?

@Getter
@AllArgsConstructor
public abstract class MailMessageTemplate {

    @NotBlank
    protected String from;
    @NotBlank
    protected String subject;
    protected String replyTo;
    protected boolean htmlBody = false;

    public abstract boolean isTemplateReady();

    public abstract void buildMailBodyFromProperties(Object obj);

    public abstract String getMailBody();

}
