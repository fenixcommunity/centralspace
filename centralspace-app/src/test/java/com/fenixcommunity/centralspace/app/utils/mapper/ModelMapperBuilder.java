package com.fenixcommunity.centralspace.app.utils.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.internal.util.Strings;
import org.modelmapper.spi.MatchingStrategy;
import org.modelmapper.spi.PropertyType;

class ModelMapperBuilder {
   private final ModelMapper modelMapper = new ModelMapper();

    ModelMapperBuilder withUsingLombokBuilder() {
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

       modelMapper.getConfiguration()
               .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setMethodAccessLevel(Configuration.AccessLevel.PROTECTED); // Determines which methods and fields are eligible for matching based on accessibility

    ModelMapper build() {
        return modelMapper;
    }
}
