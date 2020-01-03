package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.app.configuration.annotation.IgnoreDuringScan;
import com.fenixcommunity.centralspace.app.configuration.mail.MailGatewayConfig;
import com.fenixcommunity.centralspace.app.configuration.restcaller.RestTemplateConfig;
import com.fenixcommunity.centralspace.app.configuration.security.advancedsecurity.AdvancedSecurityConfig;
import com.fenixcommunity.centralspace.app.configuration.security.autosecurity.AutoSecurityConfig;
import com.fenixcommunity.centralspace.app.configuration.swaggerdoc.SwaggerConfig;
import com.fenixcommunity.centralspace.domain.configuration.DomainConfig;
import com.fenixcommunity.centralspace.utilities.configuration.UtilitiesConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(value = {"com.fenixcommunity.centralspace.app"},
        excludeFilters = @Filter(IgnoreDuringScan.class))
@Import({DomainConfig.class,
        UtilitiesConfig.class,
        AutoSecurityConfig.class,
        AdvancedSecurityConfig.class,
        MailGatewayConfig.class,
        FilterApiConfig.class,
        AopConfg.class,
        SwaggerConfig.class,
        RestTemplateConfig.class
})
//@Profile(Profiles.STANDALONE_PROFILE)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CentralspaceApplicationConfig {
    //todo profiles!!!
}
