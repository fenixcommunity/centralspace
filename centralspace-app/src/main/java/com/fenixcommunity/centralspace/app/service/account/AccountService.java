package com.fenixcommunity.centralspace.app.service.account;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.fenixcommunity.centralspace.app.configuration.annotation.MethodMonitoring;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.role.RoleGroup;
import com.fenixcommunity.centralspace.domain.repository.permanent.account.AccountRepository;
import com.fenixcommunity.centralspace.domain.repository.permanent.role.RoleGroupRepository;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountService {
    public static final String ASSIGN_ROLE_TO_ACCOUNT_FAILED_MESSAGE = "Assign role to account failed";

    private final AccountRepository accountRepository;
    private final RoleGroupRepository roleGroupRepository;

    @MethodMonitoring
    @Transactional(rollbackForClassName = "ServiceFailedException", rollbackFor = RuntimeException.class)
    public Account createAccount(@NonNull final Account account) {
        if (account.getAddress() == null) {
            throw new ServiceFailedException("Update failed. No address data for account.");
        }

        return accountRepository.save(account);
    }

    public Account createAccount(@NonNull final String username,
                                 @NonNull final String mail,
                                 @NonNull final Address address,
                                 final RoleGroup roleGroup) {
        Account account = Account.builder()
                .login(username)
                .mail(mail)
                .address(address)
                .roleGroup(roleGroup)
                .build();
        return accountRepository.save(account);
    }

    public void deleteById(final long id) {
        accountRepository.deleteById(id);
    }

    public Optional<Account> findById(final long id) {
        return accountRepository.findById(id);
    }

    @Async
    @MethodMonitoring
    public CompletableFuture<List<Account>> findAll() {
        final CompletableFuture<List<Account>> completableFuture = new CompletableFuture<>();
        final Sort sort = Sort.by("login").descending().and(Sort.by("mail")); // JpaSort.unsafe("LENGTH(name)"
        return completableFuture
                .completeAsync(() -> unmodifiableList(accountRepository.findAll(sort)))
                .exceptionally(exception -> emptyList())
                .thenApply(Collections::unmodifiableList);
    }

    @Async
    public CompletableFuture<Optional<Account>> findByLogin(final String login) {
        return completedFuture(Optional.of(accountRepository.findByLogin(login)));
    }

    public void assignRoleToAccount(final Long accountId,final  Set<Long> roleGroupsIds) {
        final Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ServiceFailedException(ASSIGN_ROLE_TO_ACCOUNT_FAILED_MESSAGE));
        final List<RoleGroup> roleGroups = roleGroupRepository.findAllById(roleGroupsIds);
        roleGroups.addAll(account.getRoleGroups());
        account.setRoleGroups(ImmutableSet.copyOf(roleGroups));
        accountRepository.save(account);
    }
}
