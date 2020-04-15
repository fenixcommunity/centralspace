package com.fenixcommunity.centralspace.app.configuration.mail;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import com.fenixcommunity.centralspace.utilities.mail.properties.MailContent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:mail-gateway.yml", factory = YamlFetcher.class)
@ConfigurationProperties(prefix = "mailgateway")
@Getter @Setter @FieldDefaults(level = PRIVATE)
@Deprecated // no immutable object!! // example case to show many options // linkTo AmazonProperties
class MailProperties {

    private String host;
    @Min(25)
    @Max(1000)
    private int port;
    private String protocol;
    private String username;
    private String password;

    private MailContent content;
}