package com.fenixcommunity.centralspace.domain.core.graphql.exception;

import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class AddressNotFoundGraphQLException extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions;

    public AddressNotFoundGraphQLException(final String message, final Long invalidAddressId) {
        super(message);
        extensions = newHashMap();
        extensions.put("invalidAddressId", invalidAddressId);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return List.of(new SourceLocation(0, 0));
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}