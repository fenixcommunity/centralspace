package com.fenixcommunity.centralspace.app.configuration.security.handler;

import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppRegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
//    private IUserService service;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        // TODO
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        constructEmailMessage(event, user, token);
    }

    private void constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
    }


}