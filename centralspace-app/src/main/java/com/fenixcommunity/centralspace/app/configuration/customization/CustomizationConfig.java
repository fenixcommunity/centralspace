package com.fenixcommunity.centralspace.app.configuration.customization;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.configuration.web.LocaleProperties;
import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:customization.yml", factory = YamlFetcher.class)
@EnableConfigurationProperties(LocaleProperties.class)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomizationConfig {
}
