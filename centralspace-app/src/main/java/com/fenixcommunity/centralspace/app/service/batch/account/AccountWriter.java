package com.fenixcommunity.centralspace.app.service.batch.account;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.util.List;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountWriter implements ItemWriter<Account>, StepExecutionListener {

    @Override
    public void write(final List<? extends Account> accounts) {
        accounts.forEach(account -> System.out.println(account.getLogin()));
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
