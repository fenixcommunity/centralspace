package com.fenixcommunity.centralspace.app.configuration.features;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.configuration.features.flags.BatchFeature;
import com.fenixcommunity.centralspace.app.configuration.features.flags.SmsFeature;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "features")
@Getter @FieldDefaults(level = PRIVATE)
public class AppFeatures {
    private final BatchFeature batchFeature;
    private final SmsFeature smsFeature;

    @ConstructorBinding
    public AppFeatures(BatchFeature batchFeature, SmsFeature smsFeature) {
        this.batchFeature = batchFeature;
        this.smsFeature = smsFeature;
    }
}