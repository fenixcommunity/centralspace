package com.fenixcommunity.centralspace.domain.core.graphql.mapper;

import com.fenixcommunity.centralspace.domain.core.graphql.dto.AddressGraphQLDto;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.utilities.mapper.Mappable;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.enums.MappingType;
import com.googlecode.jmapper.api.enums.NullPointerControl;
import org.springframework.stereotype.Component;

@Component
public class AddressGraphQLMapper implements Mappable<Address, AddressGraphQLDto> {

    @Override
    public AddressGraphQLDto mapToDto(final Address address) {
        return new JMapper<>(AddressGraphQLDto.class, Address.class).getDestination(address, NullPointerControl.ALL, MappingType.ONLY_VALUED_FIELDS);
    }

    @Override
    public Address mapFromDto(final AddressGraphQLDto addressGraphQLDto) {
        return new JMapper<>(Address.class, AddressGraphQLDto.class).getDestination(addressGraphQLDto);
    }
}
