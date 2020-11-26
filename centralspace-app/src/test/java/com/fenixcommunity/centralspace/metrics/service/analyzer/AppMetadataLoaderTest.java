package com.fenixcommunity.centralspace.metrics.service.analyzer;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.isJSONValid;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.fenixcommunity.centralspace.app.configuration.annotation.MethodMonitoring;
import com.fenixcommunity.centralspace.app.service.serviceconnector.RemoteService;
import io.github.classgraph.ClassInfoList;
import org.junit.jupiter.api.Test;

class AppMetadataLoaderTest {

    @Test
    void getClassGraphScanResultTest() {
        AppMetadataLoader<MethodMonitoring> appMetadataLoaderForSpringService = new AppMetadataLoader<>();
        List<ClassInfoList> classGraphScanResultForAnnotation = appMetadataLoaderForSpringService.getAllGraphScanResultForAnnotation(MethodMonitoring.class);

        assertThat(classGraphScanResultForAnnotation).extracting(classInfos -> classInfos.size()).contains(1);
    }

    @Test
    void getMetadataInformationInJsonFormatTest() {
        AppMetadataLoader<Object> appMetadataLoader = new AppMetadataLoader<>();
        String metadataInformationInJsonFormat = appMetadataLoader.getMetadataInformationInJsonFormat();

        assertThat(isJSONValid(metadataInformationInJsonFormat)).isTrue();
    }

    @Test
    void getMetadataInformationTest() {
        AppMetadataLoader<RemoteService> appMetadataLoaderForRemoteService = new AppMetadataLoader<>();
        MetadataInformation appMetadataLoader = appMetadataLoaderForRemoteService.getMetadataInformation(RemoteService.class);

        long serviceRepresentation = appMetadataLoader.getServiceRepresentation();
        assertThat(serviceRepresentation).isEqualTo(2);
    }

}