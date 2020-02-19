package com.fenixcommunity.centralspace.app.configuration.web;

import static lombok.AccessLevel.PRIVATE;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class HttpSessionConfig {

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(final HttpSessionEvent se) {
                log.info("Session Created with session id+" + se.getSession().getId());
            }

            @Override
            public void sessionDestroyed(final HttpSessionEvent se) {
                log.info("Session Destroyed, Session id:" + se.getSession().getId());

            }
        };
    }

    // called when session attribute added
    @Bean
    public HttpSessionAttributeListener httpSessionAttributeListener() {
        return new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(final HttpSessionBindingEvent se) {
                log.info("Attribute Added following information");
                log.info("Attribute Name:" + se.getName());
                log.info("Attribute Value:" + se.getValue());
            }

            @Override
            public void attributeRemoved(final HttpSessionBindingEvent se) {
                log.info("attributeRemoved");
            }

            @Override
            public void attributeReplaced(final HttpSessionBindingEvent se) {
                log.info("Attribute Replaced following information");
                log.info("Attribute Name:" + se.getName());
                log.info("Attribute Old Value:" + se.getValue());
            }
        };
    }
}
