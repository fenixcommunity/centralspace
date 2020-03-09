package com.fenixcommunity.centralspace.app.utils.mapper;


import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
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
        return null;
        implement and remove AccountMapperOld
    }
}