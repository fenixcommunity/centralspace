package com.fenixcommunity.centralspace.app.rest.mapper;


import static java.util.stream.Collectors.toUnmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto.AccountDtoBuilder;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.spi.DestinationSetter;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountMapper {
    private final ModelMapper mapToDtoStrategy;
    private final ModelMapper mapFromDtoStrategy;

    public AccountMapper(OperationLevel operationLevel) {
        mapToDtoStrategy = prepareMapToDtoStrategy(operationLevel);
        mapFromDtoStrategy = prepareMapFromDtoStrategy(operationLevel);
    }

    public AccountDto mapToDto(final Account account) {
        return mapToDtoStrategy.map(account, AccountDtoBuilder.class).build();
    }
    public List<AccountDto> mapToDtoList(final List<Account> accounts) {
        return accounts.stream().map(this::mapToDto).collect(toUnmodifiableList());
    }

    public Account mapFromDto(final AccountDto accountDto) {
        return mapFromDtoStrategy.map(accountDto, Account.class);
    }
    public List<Account> mapFromDtoList(final List<AccountDto> accountsDto) {
        return accountsDto.stream().map(this::mapFromDto).collect(toUnmodifiableList());
    }

    private ModelMapper prepareMapToDtoStrategy(final OperationLevel operationLevel) {
        ModelMapper modelMapper = new ModelMapperBuilder()
                .withUsingLombokBuilderForDestination()
                .withMethodAccessLevelToMapping(Configuration.AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();

        var typeMap = modelMapper.createTypeMap(Account.class, AccountDtoBuilder.class);
        final Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(Account::getId, AccountDtoBuilder::id));

        typeMap.addMapping(Account::getAddress, AccountDtoBuilder::contactDetailsDtoFromAddress);

        return modelMapper;
    }

    private ModelMapper prepareMapFromDtoStrategy(final OperationLevel operationLevel) {
        ModelMapper modelMapper = new ModelMapperBuilder()
                .withUsingLombokBuilderForSource()
                .withMethodAccessLevelToMapping(Configuration.AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();

        var typeMap = modelMapper.createTypeMap(AccountDto.class, Account.class);
        final Condition shouldMapId = ctx -> OperationLevel.HIGH == operationLevel;
        typeMap.addMappings(m -> m.when(shouldMapId).map(AccountDto::getId, Account::setId));

        final DestinationSetter<Account, Object> setContactDetails =  AccountMapperHelper::mapContactDetails;
        typeMap.addMapping(AccountDto::getContactDetailsDto, setContactDetails);

        return modelMapper;
    }

}