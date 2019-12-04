package com.fenixcommunity.centralspace.utilities.mail.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

//TODO 27.11.2019 what about getters?

@Getter
@AllArgsConstructor
public abstract class MailMessageTemplate {

    @NotBlank
    protected String from;
    @NotBlank
    protected String subject;
    @NotBlank
    protected String body;
    protected String replyTo;
    protected boolean htmlBody = false;

    public boolean isTemplateReady() {
        return isNotEmpty(from) && isNotEmpty(subject) && isNotEmpty(body);
    }
}
