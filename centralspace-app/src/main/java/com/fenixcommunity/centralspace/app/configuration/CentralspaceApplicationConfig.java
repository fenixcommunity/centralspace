package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.domain.configuration.DomainConfig;
import com.fenixcommunity.centralspace.utilities.configuration.UtilitiesConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace.app"})
@Import({DomainConfig.class,
        UtilitiesConfig.class,
        MailGatewayConfig.class,
        FilterApiConfig.class,
        AopConfiguration.class,
        SwaggerConfig.class
})
@SpringBootApplication
public class CentralspaceApplicationConfig {
}
