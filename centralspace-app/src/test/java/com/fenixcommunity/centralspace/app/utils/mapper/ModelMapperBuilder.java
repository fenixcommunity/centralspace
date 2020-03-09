package com.fenixcommunity.centralspace.app.utils.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.internal.util.Strings;
import org.modelmapper.spi.MatchingStrategy;
import org.modelmapper.spi.NameTokenizer;
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

    ModelMapperBuilder withNameConvention(final NameTokenizer sourceNameConvention, final NameTokenizer destinationNameConvention) {
        modelMapper.getConfiguration()
                .setSourceNameTokenizer(sourceNameConvention)
                .setDestinationNameTokenizer(destinationNameConvention);
        return this;
    }

    // Determines which methods and fields are eligible for matching based on accessibility
    ModelMapperBuilder withMethodAccessLevelToMapping(final AccessLevel accessLevelToMapping) {
        modelMapper.getConfiguration()
                .setMethodAccessLevel(accessLevelToMapping);
        return this;
    }

    ModelMapper build() {
        return modelMapper;
    }
}
