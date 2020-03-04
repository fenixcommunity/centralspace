package com.fenixcommunity.centralspace.app.utils.mapper;


import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import org.modelmapper.ModelMapper;

//public class AccountMap extends PropertyMap<AccountDto2, Account> {
//        @Override
//        protected void configure() {
//            map(source.getLogin(), destination.getLogin());
//        }
//    }
//
public class AccountMap  {
    private final ModelMapper modelMapper;

    public AccountMap() {
        modelMapper = BuilderModelMapper.init();
    }

    public AccountDto map(final Account source) {
        return modelMapper.map(source, AccountDto.AccountDtoBuilder.class).build();
    }
}