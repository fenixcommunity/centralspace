package com.fenixcommunity.centralspace.app.service.feature;

import static lombok.AccessLevel.PRIVATE;

import java.util.Set;
import java.util.stream.Collectors;

import com.fenixcommunity.centralspace.app.configuration.annotation.AppFeature;
import com.fenixcommunity.centralspace.app.configuration.features.AppFeatures;
import com.fenixcommunity.centralspace.app.configuration.features.flags.BatchFeature;
import com.fenixcommunity.centralspace.app.configuration.features.flags.SmsFeature;
import com.fenixcommunity.centralspace.metrics.service.analyzer.AppMetadataLoader;
import io.github.classgraph.FieldInfo;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppFeatureChecker {
    private final AppFeatures appFeatures;

    public BatchFeature getBatchFeatureInfo() {
        return appFeatures.getBatchFeature();
    }

    public SmsFeature getSmsFeatureInfo() {
        return appFeatures.getSmsFeature();
    }

    public Set<String> getFeatureList() {
        return new AppMetadataLoader<AppFeature>().getFieldsOfClassForAnnotation(AppFeature.class)
                .stream()
                .map(FieldInfo::getName)
                .collect(Collectors.toSet());
    }
}
