package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import org.springframework.mail.MailException;

public interface MailService {

    void sendBasicMail(String to) throws MailException;

    void sendRegistrationMailWithAttachment(String to) throws MailException;
}