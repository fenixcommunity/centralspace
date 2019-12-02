package com.fenixcommunity.centralspace.app.utils.mail.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

//TODO 27.11.2019 what about getters?

public abstract class MailMessageTemplate {

    @Nullable
    @Getter
    protected String from;
    @Nullable
    @Getter
    protected String[] to;

    @Nullable
    @Getter
    protected String subject;
    @Nullable
    @Getter
    protected String text;

    @Getter
    @Setter
    protected String replyTo;

    @Getter
    @Setter
    protected String[] cc;

    @Getter
    @Setter
    protected String[] bcc;

    @Getter
    @Setter
    protected Date sentDate;

    @Getter
    protected boolean htmlBody = false;

    public abstract void setFrom(@Nullable String from);

    public abstract void setTo(@Nullable String[] to);

    public abstract void setSubject(@Nullable String subject);

    public abstract void setText(@Nullable String text);

    public abstract void setHtmlBody(boolean isHtmlBody);
}
