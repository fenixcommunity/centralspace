package com.fenixcommunity.centralspace.app.service.feature;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import com.fenixcommunity.centralspace.app.configuration.features.AppFeatures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AppFeatureCheckerTest {

    @InjectMocks
    private AppFeatureChecker appFeatureChecker;

    @MockBean
    private AppFeatures appFeatures;


    @Test
    public void getMetadataInformationTest() {
        Set<String> featureList = appFeatureChecker.getFeatureList();

        assertThat(featureList).contains("accountLoginUpdater");
    }
}

