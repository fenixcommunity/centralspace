package com.fenixcommunity.centralspace.app.helper.mapper;

import static java.util.stream.Collectors.toUnmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.fenixcommunity.centralspace.app.rest.mapper.ModelMapperBuilder;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class AccountMapperToTest {
    private final ModelMapper mapToDtoStrategy;
    private final ModelMapper mapFromDtoStrategy;

    public AccountMapperToTest(OperationLevel operationLevel) {
        mapToDtoStrategy = prepareMapToDtoStrategy(operationLevel);
        mapFromDtoStrategy = prepareMapFromDtoStrategy(operationLevel);
    }

    public AccountDtoToTest mapToDto(final Account account) {
        return mapToDtoStrategy.map(account, AccountDtoToTest.AccountDtoToTestBuilder.class).build();
    }

    public List<AccountDtoToTest> mapToDtoList(final List<Account> accounts) {
        return accounts.stream()
                .map(this::mapToDto)
                .collect(toUnmodifiableList());
    }

    public Account mapFromDto(final AccountDtoToTest accountDto) {
        return mapFromDtoStrategy.map(accountDto, Account.class);
    }

    public List<Account> mapFromDtoList(final List<AccountDtoToTest> accountsDto) {
        return accountsDto.stream()
                .map(this::mapFromDto)
                .collect(toUnmodifiableList());
    }

    private ModelMapper prepareMapToDtoStrategy(final OperationLevel operationLevel) {
        ModelMapper modelMapper = new ModelMapperBuilder()
                .withUsingLombokBuilderForDestination()
                .withMethodAccessLevelToMapping(Configuration.AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();

        var typeMap = modelMapper.createTypeMap(Account.class, AccountDtoToTest.AccountDtoToTestBuilder.class);
        final Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(Account::getId, AccountDtoToTest.AccountDtoToTestBuilder::id));

        return modelMapper;
    }

    private ModelMapper prepareMapFromDtoStrategy(final OperationLevel operationLevel) {
        ModelMapper modelMapper = new ModelMapperBuilder()
                .withUsingLombokBuilderForSource()
                .withMethodAccessLevelToMapping(Configuration.AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();

        var typeMap = modelMapper.createTypeMap(AccountDtoToTest.class, Account.class);
        final Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(AccountDtoToTest::getId, Account::setId));

        return modelMapper;
    }

}