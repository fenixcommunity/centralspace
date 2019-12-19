package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.app.configuration.mail.MailGatewayConfig;
import com.fenixcommunity.centralspace.app.configuration.profile.Profiles;
import com.fenixcommunity.centralspace.app.configuration.swaggerdoc.SwaggerConfig;
import com.fenixcommunity.centralspace.domain.configuration.DomainConfig;
import com.fenixcommunity.centralspace.utilities.configuration.UtilitiesConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace.app"})
@Import({DomainConfig.class,
        UtilitiesConfig.class,
        SecurityConfig.class,
        MailGatewayConfig.class,
        FilterApiConfig.class,
        AopConfg.class,
        SwaggerConfig.class
})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Profile(Profiles.STANDALONE_PROFILE)
public class CentralspaceApplicationConfig {
    //todo profiles!!!
}
