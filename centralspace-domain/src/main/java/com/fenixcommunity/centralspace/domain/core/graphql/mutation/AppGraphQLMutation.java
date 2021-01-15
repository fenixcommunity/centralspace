package com.fenixcommunity.centralspace.domain.core.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fenixcommunity.centralspace.domain.core.graphql.dto.AccountGraphQLDto;
import com.fenixcommunity.centralspace.domain.core.graphql.exception.AccountNotFoundGraphQLException;
import com.fenixcommunity.centralspace.domain.core.graphql.mapper.AccountGraphQLMapper;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.repository.permanent.AddressRepository;
import com.fenixcommunity.centralspace.domain.repository.permanent.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Component @Validated
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppGraphQLMutation implements GraphQLMutationResolver {
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final AccountGraphQLMapper accountGraphQLMapper;

    @Transactional
    public AccountGraphQLDto updateAccount(@Valid AccountGraphQLDto accountGraphQlDto) {
        final Long id = accountGraphQlDto.getId();
        final Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundGraphQLException("The account to be updated was not found", id));
        account.setNip(accountGraphQlDto.getIdentifier());
        Account updatedAccount = accountRepository.save(account);

        return accountGraphQLMapper.mapToDto(updatedAccount);
    }

    public boolean deleteAddress(@NonNull final Long id) {
        boolean existed = addressRepository.existsById(id);
        addressRepository.deleteById(id);

        return existed;
    }
}