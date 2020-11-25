package com.fenixcommunity.centralspace.app.service.batch.account;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountProcessor implements ItemProcessor<Account, Account>, StepExecutionListener {

    @Override
    public Account process(final Account account) {
        return account;
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
