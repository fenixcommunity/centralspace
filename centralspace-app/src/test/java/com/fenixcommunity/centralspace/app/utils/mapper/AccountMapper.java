package com.fenixcommunity.centralspace.app.utils.mapper;


import static com.fenixcommunity.centralspace.app.rest.dto.AccountDto.*;

import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper() {
        modelMapper = new ModelMapperBuilder()
                .withUsingLombokBuilder()
                .withMatchingStrategy(MatchingStrategies.STRICT)
                .build();
    }

    public AccountDto map(final Account account) {
        return modelMapper
                .typeMap(Account.class, AccountDtoBuilder.class)
                .addMappings(mapper -> {
                    mapper.map(source -> source.getPasswords().get(0).getPasswordType(),
                            AccountDtoBuilder::passwordType);
                    mapper.map(Account::getMail,
                            AccountDtoBuilder::mail);
                })
                .map(account).build();
    }
}