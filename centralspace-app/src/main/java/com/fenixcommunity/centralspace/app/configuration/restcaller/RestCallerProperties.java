package com.fenixcommunity.centralspace.app.configuration.restcaller;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_EMPTY;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:security.yml", factory = YamlFetcher.class)
@ConfigurationProperties(prefix = "restcaller.credentials")
@Getter @FieldDefaults(level = PRIVATE)
class RestCallerProperties {
    private String username;
    private String password;

    @ConstructorBinding
    RestCallerProperties(final String username, final String password) {
        var validator = new ValidatorFactory().getInstance(NOT_EMPTY);
        validator.validateAllWithException(username, password);
        this.username = username;
        this.password = password;
    }
}