package com.fenixcommunity.centralspace.app.configuration;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.context.annotation.ComponentScan.Filter;

import com.fenixcommunity.centralspace.app.configuration.actuatormanager.ActuatorConfig;
import com.fenixcommunity.centralspace.app.configuration.annotation.IgnoreDuringScan;
import com.fenixcommunity.centralspace.app.configuration.aop.AopConfg;
import com.fenixcommunity.centralspace.app.configuration.async.AsyncConfig;
import com.fenixcommunity.centralspace.app.configuration.aws.s3.AmazonS3Config;
import com.fenixcommunity.centralspace.app.configuration.caching.CachingConfig;
import com.fenixcommunity.centralspace.app.configuration.mail.MailGatewayConfig;
import com.fenixcommunity.centralspace.app.configuration.actuatormanager.ActuatorSwaggerConfig;
import com.fenixcommunity.centralspace.app.configuration.security.autosecurity.AutoSecurityConfig;
import com.fenixcommunity.centralspace.app.configuration.security.autosecurity.MethodAutoSecurityConfig;
import com.fenixcommunity.centralspace.app.configuration.swaggerdoc.SwaggerConfig;
import com.fenixcommunity.centralspace.app.configuration.web.FilterApiConfig;
import com.fenixcommunity.centralspace.app.configuration.web.HttpSessionConfig;
import com.fenixcommunity.centralspace.app.configuration.web.WebConfig;
import com.fenixcommunity.centralspace.metrics.config.MetricsConfig;
import com.fenixcommunity.centralspace.domain.configuration.H2DomainConfig;
import com.fenixcommunity.centralspace.domain.configuration.PostgresDomainConfig;
import com.fenixcommunity.centralspace.utilities.configuration.UtilitiesConfig;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(value = {"com.fenixcommunity.centralspace.app"},
        excludeFilters = @Filter(IgnoreDuringScan.class))
@Import({
        // domain
        PostgresDomainConfig.class,
        H2DomainConfig.class,
        // security
        AutoSecurityConfig.class,
        MethodAutoSecurityConfig.class,
//      AdvancedSecurityConfig.class,
        // web
        WebConfig.class,
        FilterApiConfig.class,
        HttpSessionConfig.class,
//      RestCallerStrategy.class, WebClientConfig.class -> hidden config
        // aws
        AmazonS3Config.class,
        // utils
        UtilitiesConfig.class,
        MailGatewayConfig.class,
        AopConfg.class,
        SwaggerConfig.class,
        MetricsConfig.class,
        ActuatorConfig.class,
        ActuatorSwaggerConfig.class,
        CachingConfig.class,
        AsyncConfig.class
})
//@Profile(Profiles.STANDALONE_PROFILE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CentralspaceApplicationConfig {
    //todo profiles!!!
}
