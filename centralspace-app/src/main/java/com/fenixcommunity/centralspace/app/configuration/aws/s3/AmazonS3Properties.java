package com.fenixcommunity.centralspace.app.configuration.aws.s3;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "aws.app") @PropertySource("classpath:security.yml")
@Getter @FieldDefaults(level = PRIVATE)
class AmazonS3Properties {
    private String keyId;
    private String secretKey;

    @ConstructorBinding
    public AmazonS3Properties(String keyId, String secretKey) {
        this.keyId = keyId;
        this.secretKey = secretKey;
    }
}