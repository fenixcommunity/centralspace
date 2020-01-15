package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import org.springframework.mail.MailException;

public interface MailService {

    void sendBasicMail(final String to) throws MailException;

    void sendRegistrationMailWithAttachment(final String to) throws MailException;
}