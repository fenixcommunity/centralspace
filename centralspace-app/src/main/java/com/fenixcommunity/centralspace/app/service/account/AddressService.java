package com.fenixcommunity.centralspace.app.service.account;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.repository.permanent.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class AddressService {
    private final AddressRepository addressRepository;

    public Address createAddress(final String country) {
        final Address address = Address.builder()
                .country(country)
                .build();
        return addressRepository.save(address);
    }
}
