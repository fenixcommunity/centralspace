package com.fenixcommunity.centralspace.app.service.batch;

import com.fenixcommunity.centralspace.app.configuration.batch.BatchConfig;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BatchConfig.class)
public class BatchLauncherTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
}