package com.fenixcommunity.centralspace.app.service.batch;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.util.Collections;

import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class BatchLauncher {

    @Qualifier("accountBatch")
    private final Job accountBatch;
    private final JobLauncher jobLauncher;

    public BatchStatus launchAccountBatch() {
        try {
            final JobExecution jobExecution = jobLauncher.run(accountBatch, new JobParameters(Collections.emptyMap()));
            return jobExecution.getStatus();
        } catch (JobExecutionException e) {
            throw new ServiceFailedException(e);
        }
    }
}
