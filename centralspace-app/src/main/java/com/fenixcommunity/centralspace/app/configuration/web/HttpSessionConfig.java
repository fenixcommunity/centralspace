package com.fenixcommunity.centralspace.app.configuration.web;

import static lombok.AccessLevel.PRIVATE;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class HttpSessionConfig {

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(final HttpSessionEvent se) {
                System.out.println("Session Created with session id+" + se.getSession().getId());
            }

            @Override
            public void sessionDestroyed(final HttpSessionEvent se) {
                System.out.println("Session Destroyed, Session id:" + se.getSession().getId());

            }
        };
    }

    // called when session attribute added
    @Bean
    public HttpSessionAttributeListener httpSessionAttributeListener() {
        return new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(final HttpSessionBindingEvent se) {
                System.out.println("Attribute Added following information");
                System.out.println("Attribute Name:" + se.getName());
                System.out.println("Attribute Value:" + se.getValue());
            }

            @Override
            public void attributeRemoved(final HttpSessionBindingEvent se) {
                System.out.println("attributeRemoved");
            }

            @Override
            public void attributeReplaced(final HttpSessionBindingEvent se) {
                System.out.println("Attribute Replaced following information");
                System.out.println("Attribute Name:" + se.getName());
                System.out.println("Attribute Old Value:" + se.getValue());
            }
        };
    }
}
