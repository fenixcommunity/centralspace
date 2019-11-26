package com.fenixcommunity.centralspace.app.service.email.emailsender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "emailgateway")
@PropertySource("classpath:services.properties")
@Getter @Setter
public class EmailProperties {

    private String host;
    private int port;
    private String username;
    private String password;

//todo we can also set properties value
}
