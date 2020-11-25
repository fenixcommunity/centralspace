package com.fenixcommunity.centralspace.app.service.batch.account;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class AccountReader implements ItemReader<Account>, StepExecutionListener {

    // todo add repository
    private final Queue<String> accountLoginsQueue = new ArrayDeque<>(List.of("accountLogin1", "accountLogin2"));

    @Override
    public Account read() {
        if (accountLoginsQueue.isEmpty()) {
            return null;
        }
        return Account.builder().login(accountLoginsQueue.poll()).build();

    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

}