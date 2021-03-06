package com.fenixcommunity.centralspace.utilities.mail;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter @FieldDefaults(level = PRIVATE)
public class MailBuilder {

    public MailBuilder(MailMessageTemplate template) {
        from = template.getFrom();
        body = template.getMailBody();
        subject = template.getSubject();
    }

    private String from;
    private List<@NotBlank String> to = new ArrayList<>();
    private String subject;
    @Setter(AccessLevel.NONE)
    private String body;
    private String replyTo;
    private String[] cc;
    private String[] bcc;
    private Date sentDate;
    private boolean htmlBody = false;

    public void setBody(String body) {
        if (isNotEmpty(body)) {
            this.body = this.body + body;
        }
        this.body = body;
    }

    public void addTo(@Email String to) {
        this.to.add(to);
    }

    public String[] getToArray() {
        return to.toArray(new String[0]);
    }

    public boolean isReadyToSend() {
        return isNotEmpty(from) && !getTo().isEmpty() && isNotEmpty(subject) && isNotEmpty(body);
    }
}
