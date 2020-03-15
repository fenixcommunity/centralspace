package com.fenixcommunity.centralspace.app.rest.mapper;


import static lombok.AccessLevel.PRIVATE;

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

    public AccountMapper() {
        modelMapper = new ModelMapperBuilder()
                .withUsingLombokBuilder()
                .withMethodAccessLevelToMapping(Configuration.AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();
    }

    public AccountDto mapToDto(final Account account, final OperationLevel operationLevel) {
        var typeMap = modelMapper.createTypeMap(Account.class, AccountDtoBuilder.class);
        Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(Account::getId, AccountDtoBuilder::id));

        return modelMapper.map(account, AccountDtoBuilder.class).build();
    }

    public Account mapFromDto(final AccountDto accountDto, final OperationLevel operationLevel) {
        var typeMap = modelMapper.createTypeMap(AccountDto.class, Account.class);
        Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(AccountDto::getId, Account::setId));

        return modelMapper.map(accountDto, Account.class);
    }
}