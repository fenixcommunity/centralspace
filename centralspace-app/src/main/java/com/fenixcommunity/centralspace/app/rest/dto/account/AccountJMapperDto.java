package com.fenixcommunity.centralspace.app.rest.dto.account;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import lombok.Data;

@Data
public class AccountJMapperDto {
    private Long id;

    @JMap("login")
    private String uniqueLogin;

    @JMap
    private String mail;

    @JMap("nip")
    private String identifier;

    @JMap("address")
    private ContactDetailsDto contactDetails;

    @JMapConversion(from = {"address"}, to = {"contactDetails"})
    public ContactDetailsDto contactDetailsConversion(Address address) {
        return new ContactDetailsDto(address.getCountry(), null);
    }

}
