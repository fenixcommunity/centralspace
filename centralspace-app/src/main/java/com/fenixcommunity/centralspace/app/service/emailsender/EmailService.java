package com.fenixcommunity.centralspace.app.service.emailsender;

public interface EmailService {
    void sendEmail(String fromAddress, String toAddress, String subject, String body);
}