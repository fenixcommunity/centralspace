package com.fenixcommunity.centralspace.app.utils.email.template;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
public class RegistrationSimpleMailMessage implements MailMessageTemplate {

    @Nullable
    private String from;
    @Nullable
    private String replyTo;
    @Nullable
    private String[] to;
    @Nullable
    private String[] cc;
    @Nullable
    private String[] bcc;
    @Nullable
    private Date sentDate;
    @Nullable
    private String subject;
    @Nullable
    private String text;

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    @Override
    public void setTo(String to) {
        this.to = new String[] {to};
    }

    @Override
    public void setTo(String... to) {
        this.to = to;
    }

    @Override
    public void setCc(String cc) {
        this.cc = new String[] {cc};
    }

    @Override
    public void setCc(String... cc) {
        this.cc = cc;
    }

    @Override
    public void setBcc(String bcc) {
        this.bcc = new String[] {bcc};
    }

    @Override
    public void setBcc(String... bcc) {
        this.bcc = bcc;
    }

    @Override
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }


}
