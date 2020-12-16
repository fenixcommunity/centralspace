package com.fenixcommunity.centralspace.domain.core.graphql;


import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class Query implements GraphQLQueryResolver {

    public Iterable<Address> findAllAddresses() {
        return List.of(new Address("sd", null));
    }

    public Iterable<Account> findAllAccounts() {
        return List.of(new Account(6L));
    }

    public long countAddresses() {
        return 3;
    }
    public long countAccounts() {
        return 4;
    }
}