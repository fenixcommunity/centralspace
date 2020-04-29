package com.fenixcommunity.centralspace.app.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.fenixcommunity.centralspace.app.configuration.annotation.MethodMonitoring;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.repository.permanent.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountService {

    private final AccountRepository accountRepository;

    public Account save(final Account account) {
        return accountRepository.save(account);
    }

    public void delete(final Account account) {
        accountRepository.delete(account);
    }

    public void deleteById(final Long id) {
        accountRepository.deleteById(id);
    }

    public Optional<Account> findById(final Long id) {
        return accountRepository.findById(id);
    }

    @MethodMonitoring
    @Async
    public CompletableFuture<List<Account>> findAll() {
        final CompletableFuture<List<Account>> completableFuture = new CompletableFuture<>();
        return completableFuture
                .completeAsync(() -> unmodifiableList(accountRepository.findAll()))
                .exceptionally(exception -> emptyList())
                .thenApply(Collections::unmodifiableList);
    }

    @Async
    public CompletableFuture<Account> findByLogin(final String login) {
        return completedFuture(accountRepository.findByLogin(login));
    }
}
