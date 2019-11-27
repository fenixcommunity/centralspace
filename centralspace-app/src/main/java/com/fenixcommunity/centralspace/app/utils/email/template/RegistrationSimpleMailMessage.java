package com.fenixcommunity.centralspace.app.utils.email.template;

public class RegistrationSimpleMailMessage extends MailMessageTemplate {

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
}