package com.fenixcommunity.centralspace.app.rest.mapper;

import java.util.List;

import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Address;
import org.modelmapper.MappingException;
import org.modelmapper.spi.ErrorMessage;

class AccountMapperHelper {
    static void mapContactDetails(final Account account, final Object value) {
        if (value instanceof String) {
            final String country = (String) value;
            account.setAddress(Address.builder().country(country).build());
        } else {
            throw new MappingException(List.of(new ErrorMessage("mapContactDetails error")));
        }
    }
}