package com.fenixcommunity.centralspace.domain.core.graphql.mapper;

import com.fenixcommunity.centralspace.domain.core.graphql.dto.AccountGraphQLDto;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.utilities.mapper.Mappable;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.enums.MappingType;
import org.springframework.stereotype.Component;

@Component
public class AccountGraphQLMapper implements Mappable<Account, AccountGraphQLDto> {

    @Override
    public AccountGraphQLDto mapToDto(final Account account) {
        return new JMapper<>(AccountGraphQLDto.class, Account.class).getDestination(account, MappingType.ONLY_VALUED_FIELDS);
    }

    @Override
    public Account mapFromDto(final AccountGraphQLDto accountGraphQLDto) {
        return new JMapper<>(Account.class, AccountGraphQLDto.class).getDestination(accountGraphQLDto, MappingType.ONLY_VALUED_FIELDS);
    }
}
