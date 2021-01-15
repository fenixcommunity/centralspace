package com.fenixcommunity.centralspace.domain.core.graphql.query;


import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fenixcommunity.centralspace.domain.core.graphql.dto.AccountGraphQLDto;
import com.fenixcommunity.centralspace.domain.core.graphql.dto.AddressGraphQLDto;
import com.fenixcommunity.centralspace.domain.core.graphql.mapper.AccountGraphQLMapper;
import com.fenixcommunity.centralspace.domain.core.graphql.mapper.AddressGraphQLMapper;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.repository.permanent.AddressRepository;
import com.fenixcommunity.centralspace.domain.repository.permanent.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Component
@AllArgsConstructor(access = PUBLIC)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppGraphQLQuery implements GraphQLQueryResolver {
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final AccountGraphQLMapper accountGraphQLMapper;
    private final AddressGraphQLMapper addressGraphQLMapper;

    public Iterable<AccountGraphQLDto> findAllAccounts(final int limit) {
        final Page<Account> foundAllAccountsWithPagination = accountRepository.findAll(PageRequest.of(1, limit));
        return accountGraphQLMapper.mapToDtoList(foundAllAccountsWithPagination.toList());
    }

    public long countAccounts() {
        return accountRepository.count();
    }

    public Iterable<AddressGraphQLDto> findAllAddresses(final int limit) {
        final Page<Address> foundAllAddressWithPagination = addressRepository.findAll(PageRequest.of(1, limit));
        return addressGraphQLMapper.mapToDtoList(foundAllAddressWithPagination.toList());
    }

    public long countAddresses() {
        return addressRepository.count();
    }
}