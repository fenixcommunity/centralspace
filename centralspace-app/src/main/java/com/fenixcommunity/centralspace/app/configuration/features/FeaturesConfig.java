package com.fenixcommunity.centralspace.app.configuration.features;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:features.yml", factory = YamlFetcher.class)
@EnableConfigurationProperties(AppFeatures.class)
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class FeaturesConfig {
}
