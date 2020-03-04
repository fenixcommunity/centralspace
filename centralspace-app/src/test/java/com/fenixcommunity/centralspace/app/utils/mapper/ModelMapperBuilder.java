package com.fenixcommunity.centralspace.app.utils.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Strings;
import org.modelmapper.spi.MatchingStrategy;
import org.modelmapper.spi.PropertyType;

class ModelMapperBuilder {
   private final ModelMapper modelMapper = new ModelMapper();

    ModelMapperBuilder withLombokBuilder() {
        modelMapper.getConfiguration()
                .setDestinationNamingConvention((propertyName, propertyType) -> PropertyType.METHOD.equals(propertyType))
                .setDestinationNameTransformer((name, nameableType) -> Strings.decapitalize(name));
        return this;
    }

    ModelMapperBuilder withMatchingStrategy(final MatchingStrategy matchingStrategy) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(matchingStrategy);
        return this;
    }

    ModelMapper build() {
        return modelMapper;
    }
}
