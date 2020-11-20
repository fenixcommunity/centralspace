package com.fenixcommunity.centralspace.app.configuration.customization;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.util.Set;

import com.fenixcommunity.centralspace.app.configuration.web.LocaleProperties;
import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:customization.yml", factory = YamlFetcher.class)
@EnableConfigurationProperties(LocaleProperties.class)
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomizationConfig {

    @Bean
    public Set<String> allowedLocaleLanguages(@Value("${user.allowedLocaleLanguages:}#{T(java.util.Collections).emptySet()}")
                                    Set<String> allowedLocaleLanguages) {
        return allowedLocaleLanguages;
    }
}
