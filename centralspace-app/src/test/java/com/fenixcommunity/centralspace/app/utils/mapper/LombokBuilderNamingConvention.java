package com.fenixcommunity.centralspace.app.utils.mapper;

import org.modelmapper.spi.NamingConvention;
import org.modelmapper.spi.PropertyType;

public class LombokBuilderNamingConvention implements NamingConvention {
    @Override
    public boolean applies(String propertyName, PropertyType propertyType) {
        return PropertyType.METHOD.equals(propertyType);
    }
}
