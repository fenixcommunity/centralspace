package com.fenixcommunity.centralspace.app.rest.mapper;

import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Address;

class AccountMapperHelper {
    static void mapContactDetails(final Account account, final Object value) {
        if (value instanceof String) {
            final String country = (String) value;
            account.setAddress(Address.builder().country(country).build());
        }
    }
}