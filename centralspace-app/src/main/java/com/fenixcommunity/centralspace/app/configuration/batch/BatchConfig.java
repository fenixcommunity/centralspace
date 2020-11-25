package com.fenixcommunity.centralspace.app.configuration.batch;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import javax.sql.DataSource;

import com.fenixcommunity.centralspace.app.service.batch.account.AccountProcessor;
import com.fenixcommunity.centralspace.app.service.batch.account.AccountReader;
import com.fenixcommunity.centralspace.app.service.batch.account.AccountWriter;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class BatchConfig extends DefaultBatchConfigurer {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    @Bean
    @DependsOn({"accountReader", "accountProcessor", "accountWriter"})
    public Job accountBatch(final AccountReader accountReader,
                            final AccountProcessor accountProcessor,
                            final AccountWriter accountWriter) {
        return jobs.get("chunksJob")
                .flow(steps.get("processAccounts").<Account, Account>chunk(2)
                        .reader(accountReader)
                        .processor(accountProcessor)
                        .writer(accountWriter)
                        .allowStartIfComplete(true) // opposite .preventRestart()
                        .build()).end()
                .build();
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        //todo change it and add to database job data
    }
}