package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.app.configuration.actuatormanager.ActuatorConfig;
import com.fenixcommunity.centralspace.app.configuration.actuatormanager.ActuatorSwaggerConfig;
import com.fenixcommunity.centralspace.app.configuration.annotation.IgnoreDuringScan;
import com.fenixcommunity.centralspace.app.configuration.aop.AopConfig;
import com.fenixcommunity.centralspace.app.configuration.async.AsyncConfig;
import com.fenixcommunity.centralspace.app.configuration.aws.s3.AmazonS3Config;
import com.fenixcommunity.centralspace.app.configuration.batch.BatchConfig;
import com.fenixcommunity.centralspace.app.configuration.caching.CachingConfig;
import com.fenixcommunity.centralspace.app.configuration.customization.CustomizationConfig;
import com.fenixcommunity.centralspace.app.configuration.features.FeaturesConfig;
import com.fenixcommunity.centralspace.app.configuration.mail.MailGatewayConfig;
import com.fenixcommunity.centralspace.app.configuration.security.auto.AutoSecurityConfig;
import com.fenixcommunity.centralspace.app.configuration.security.auto.MethodAutoSecurityConfig;
import com.fenixcommunity.centralspace.app.configuration.sms.SmsConfig;
import com.fenixcommunity.centralspace.app.configuration.swaggerdoc.SwaggerConfig;
import com.fenixcommunity.centralspace.app.configuration.web.FilterApiConfig;
import com.fenixcommunity.centralspace.app.configuration.web.HttpSessionConfig;
import com.fenixcommunity.centralspace.app.configuration.web.WebConfig;
import com.fenixcommunity.centralspace.domain.configuration.HibernateConfig;
import com.fenixcommunity.centralspace.domain.configuration.db.memory.H2DomainConfig;
import com.fenixcommunity.centralspace.domain.configuration.db.memory.RedisConfig;
import com.fenixcommunity.centralspace.domain.configuration.db.permanent.PostgresDomainConfig;
import com.fenixcommunity.centralspace.domain.configuration.graphql.GraphQLConfig;
import com.fenixcommunity.centralspace.metrics.config.MetricsConfig;
import com.fenixcommunity.centralspace.utilities.configuration.UtilitiesConfig;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(value = {"com.fenixcommunity.centralspace.app"},
        excludeFilters = @Filter(IgnoreDuringScan.class))
@Import({
        // domain
        PostgresDomainConfig.class,
        H2DomainConfig.class,
        RedisConfig.class,
        HibernateConfig.class,
        GraphQLConfig.class,
        // security
        AutoSecurityConfig.class,
        MethodAutoSecurityConfig.class,
//      ManualSecurityConfig.class,
        // web
        WebConfig.class,
        FilterApiConfig.class,
        HttpSessionConfig.class,
//      RestTemplateConfig.class, WebClientConfig.class, HttpClientConfig.class -> hidden config
        // aws
        AmazonS3Config.class,
        // utils
        UtilitiesConfig.class,
        MailGatewayConfig.class,
        AopConfig.class,
        SwaggerConfig.class,
        MetricsConfig.class,
        ActuatorConfig.class,
        ActuatorSwaggerConfig.class,
        CachingConfig.class,
        AsyncConfig.class,
        CustomizationConfig.class,
        BatchConfig.class,
        SmsConfig.class,
        FeaturesConfig.class,
        SpringAdminNotifierConfig.class
})
//@Profile(Profiles.STANDALONE_PROFILE)
@EnableAdminServer
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CentralspaceApplicationConfig {
}
