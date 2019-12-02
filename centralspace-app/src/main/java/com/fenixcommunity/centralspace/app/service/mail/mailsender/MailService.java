package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import org.springframework.mail.MailException;

public interface MailService {

    void sendBasicMail() throws MailException;

    void sendRegistrationMailWithAttachment() throws MailException;
}