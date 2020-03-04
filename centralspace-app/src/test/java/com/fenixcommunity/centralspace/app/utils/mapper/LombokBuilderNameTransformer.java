package com.fenixcommunity.centralspace.app.utils.mapper;

import org.modelmapper.internal.util.Strings;
import org.modelmapper.spi.NameTransformer;
import org.modelmapper.spi.NameableType;

public class LombokBuilderNameTransformer implements NameTransformer {
    @Override
    public String transform(final String name, final NameableType nameableType) {
        return Strings.decapitalize(name);
    }
}