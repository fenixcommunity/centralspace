package com.fenixcommunity.centralspace.app.utils.mail.template;

import lombok.Setter;

@Setter
public class BasicMailMessage extends MailMessageTemplate {

    @Override
    public void setFrom(String from) {
        super.from = from;
    }

    @Override
    public void setTo(String[] to) {
        super.to = to;
    }

    @Override
    public void setSubject(String subject) {
        super.subject = subject;
    }

    @Override
    public void setText(String text) {
        super.text = text;
    }

    @Override
    public void setHtmlBody(boolean isHtmlBody) {
        super.htmlBody = isHtmlBody;
    }
}