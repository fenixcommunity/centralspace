package com.fenixcommunity.centralspace.domain.core.graphql;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class Mutation implements GraphQLMutationResolver {
    public Account newAccount(String firstName, String lastName) {
        Account account = new Account();
        account.setId(5L);

        return account;
    }

    public Address newAddress(String title, String isbn, Integer pageCount, Long accountId) {
        Address address = new Address();
        address.setAccount(new Account(accountId));

        return address;
    }

    public boolean deleteAddress(Long id) {
        return true;
    }

    public Address updateAddressPageCount(Integer pageCount, Long id) {
        Address address = new Address();
        if(address == null) {
            throw new AddressNotFoundException("The address to be updated was not found", id);
        }
        address.setName("dfd");

        return address;
    }
}