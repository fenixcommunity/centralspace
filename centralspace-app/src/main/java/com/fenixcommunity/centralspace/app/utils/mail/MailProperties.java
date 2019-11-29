package com.fenixcommunity.centralspace.app.utils.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@ConfigurationProperties(prefix = "mailgateway")
@PropertySource("classpath:services.properties")
@Getter @Setter
public class MailProperties {

    private String host;
    @Min(25)
    @Max(1000)
    private int port;
    private String protocol;
    private String username;
    private String password;

    private MailContent content;

//todo we can also set properties value
}
