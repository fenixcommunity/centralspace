package com.fenixcommunity.centralspace.metrics.service.analyzer;

import static org.assertj.core.api.Assertions.assertThat;

import com.fenixcommunity.centralspace.app.service.serviceconnector.RemoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AutoServiceLoaderTest {
    @InjectMocks
    private AutoServiceLoader<RemoteService> autoServiceLoader;

    @Test
    void getMetadataInformationTest() {
        MetadataInformation metadataInformation = autoServiceLoader.getMetadataInformation(RemoteService.class);
        long serviceRepresentation = metadataInformation.getServiceRepresentation();
        assertThat(serviceRepresentation).isEqualTo(2);
    }

}