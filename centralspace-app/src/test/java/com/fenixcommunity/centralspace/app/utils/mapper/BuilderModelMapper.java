package com.fenixcommunity.centralspace.app.utils.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

class BuilderModelMapper {

    static ModelMapper init() {
       final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setDestinationNamingConvention(new LombokBuilderNamingConvention())
                .setDestinationNameTransformer(new LombokBuilderNameTransformer());
        return modelMapper;
    }
}
