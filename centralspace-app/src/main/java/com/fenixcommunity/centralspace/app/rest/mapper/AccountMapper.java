package com.fenixcommunity.centralspace.app.rest.mapper;


import static java.util.stream.Collectors.toUnmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto.AccountDtoBuilder;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper(OperationLevel operationLevel) {
        modelMapper = new ModelMapperBuilder()
                .withUsingLombokBuilder()
                .withMethodAccessLevelToMapping(Configuration.AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();
        prepareMapToDtoStrategy(operationLevel);
        prepareMapFromDtoStrategy(operationLevel);
    }

    public AccountDto mapToDto(final Account account) {
        return modelMapper.map(account, AccountDtoBuilder.class).build();
    }

    public List<AccountDto> mapToDtoList(final List<Account> accounts) {
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountDtoBuilder.class).build())
                .collect(toUnmodifiableList());
    }

    public Account mapFromDto(final AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }

    public List<Account> mapFromDtoList(final List<AccountDto> accountsDto) {
        return accountsDto.stream()
                .map(accountDto -> modelMapper.map(accountDto, Account.class))
                .collect(toUnmodifiableList());
    }

    private void prepareMapToDtoStrategy(final OperationLevel operationLevel) {
        var typeMap = modelMapper.createTypeMap(Account.class, AccountDtoBuilder.class);
        final Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(Account::getId, AccountDtoBuilder::id));
    }

    private void prepareMapFromDtoStrategy(final OperationLevel operationLevel) {
        var typeMap = modelMapper.createTypeMap(AccountDto.class, Account.class);
        final Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(AccountDto::getId, Account::setId));
    }

}