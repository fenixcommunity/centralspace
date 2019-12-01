package com.fenixcommunity.centralspace.utilities.configuration;

import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace.utilities"})
@EnableConfigurationProperties(ResourceProperties.class)
public class UtilitiesConfig {
}
