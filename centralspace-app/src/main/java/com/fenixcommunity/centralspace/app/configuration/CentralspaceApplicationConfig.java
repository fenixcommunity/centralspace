package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.app.service.emailsender.EmailProperties;
import com.fenixcommunity.centralspace.domain.configuration.DomainConfig;
import com.fenixcommunity.centralspace.utilities.configuration.UtilitiesConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace.app"})
@Import({DomainConfig.class,
        UtilitiesConfig.class,
        EmailGatewayConfig.class})
@SpringBootApplication
@EnableConfigurationProperties(EmailProperties.class)
public class CentralspaceApplicationConfig {
}
