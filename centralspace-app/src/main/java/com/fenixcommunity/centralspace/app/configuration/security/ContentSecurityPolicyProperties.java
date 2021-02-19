package com.fenixcommunity.centralspace.app.configuration.security;

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
@ConfigurationProperties(prefix = "content-security-policy")
@Getter @FieldDefaults(level = PRIVATE)
class ContentSecurityPolicyProperties {
    private String defaultJsSrc;
    private String frameSrc;
    private String imgSrc;
    private String mediaSrc;
    private String styleSrc;
    private String frontSrc;

    @ConstructorBinding
    public ContentSecurityPolicyProperties(final String defaultJsSrc,
                                           final String frameSrc,
                                           final String imgSrc,
                                           final String mediaSrc,
                                           final String styleSrc,
                                           final String frontSrc) {
        var validator = new ValidatorFactory().getInstance(NOT_EMPTY);
        validator.validateAllWithException(defaultJsSrc, frameSrc, imgSrc, mediaSrc, styleSrc, frameSrc);
        this.defaultJsSrc = defaultJsSrc;
        this.frameSrc = frameSrc;
        this.imgSrc = imgSrc;
        this.mediaSrc = mediaSrc;
        this.styleSrc = styleSrc;
        this.frontSrc = frontSrc;
    }
}