package com.fenixcommunity.centralspace.domain.core.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;

public class AddressResolver implements GraphQLResolver<Address> {
//    GraphQLResolver<T> to resolve complex types.
//    GraphQLQueryResolver to define the operations of the root Query type.
//    GraphQLMutationResolver to define the operations of the root Mutation type.
//    GraphQLSubscriptionResolver to define the operations of the root Subscription type.

    public AddressResolver() {
    }

    public Account getAccount(Address address) {
        return new Account(3L);
    }
}