package com.fenixcommunity.centralspace.app.rest.mapper.account.jmapper;

import com.fenixcommunity.centralspace.app.rest.dto.account.AccountJMapperDto;
import com.fenixcommunity.centralspace.utilities.mapper.Mappable;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.googlecode.jmapper.JMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountJMapper implements Mappable<Account, AccountJMapperDto> {
// the best performance, not possible if only builder (use Model Mapper)

    @Override
    public AccountJMapperDto mapToDto(Account account) {
        return new JMapper<>(AccountJMapperDto.class, Account.class).getDestination(account);
    }

    @Override
    public Account mapFromDto(AccountJMapperDto accountJMapperDto) {
        return new JMapper<>(Account.class, AccountJMapperDto.class).getDestination(accountJMapperDto);
    }
}
