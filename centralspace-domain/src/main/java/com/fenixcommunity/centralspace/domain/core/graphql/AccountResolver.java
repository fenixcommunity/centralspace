package com.fenixcommunity.centralspace.domain.core.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;

public class AccountResolver implements GraphQLResolver<Account> {

    public Address getAddress(Account account) {
        return new Address("Poland", account);
    }
}
