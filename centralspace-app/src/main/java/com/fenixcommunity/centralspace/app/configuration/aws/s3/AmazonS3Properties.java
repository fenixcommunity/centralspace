package com.fenixcommunity.centralspace.app.configuration.aws.s3;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

//@PropertySource(value = "classpath:security.yml", factory = YamlFetcher.class)  removed -> values from spring cloud vault
@ConfigurationProperties(prefix = "aws.app")
@Getter @FieldDefaults(level = PRIVATE)
class AmazonS3Properties {
    // or use Yaml fetcher -> snakeyaml
    private String keyId;
    private String secretKey;

    @ConstructorBinding
    public AmazonS3Properties(String keyId, String secretKey) {
        this.keyId = keyId != null ? keyId : "empty";
        this.secretKey = secretKey != null ? secretKey : "empty";
    }
}