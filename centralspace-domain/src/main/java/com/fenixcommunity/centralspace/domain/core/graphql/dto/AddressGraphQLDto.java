package com.fenixcommunity.centralspace.domain.core.graphql.dto;

import java.util.List;

import com.fenixcommunity.centralspace.domain.core.graphql.mapper.AccountGraphQLMapper;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AddressGraphQLDto {
    @JMap
    private Long id;
    @JMap
    private String country;
    @JMap
    private String city;
    @JMap
    private List<AccountGraphQLDto> accounts;

    @JMapConversion(from = {"accounts"}, to = {"accounts"})
    public List<AccountGraphQLDto> accountsMapper(final List<Account> accounts) {
        return new AccountGraphQLMapper().mapToDtoList(accounts);
    }
}
