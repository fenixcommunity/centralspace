package com.fenixcommunity.centralspace.domain.core.graphql.exception;

import java.util.List;
import java.util.stream.Collectors;

import com.fenixcommunity.centralspace.utilities.common.DevTool;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("unused")
public class DefaultGraphQLErrorHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(final List<GraphQLError> errors) {
        final List<GraphQLError> clientErrors = errors.stream()
                .filter(this::isClientError)
                .collect(Collectors.toList());

        final List<GraphQLError> serverErrors = errors.stream()
                .filter(e -> !isClientError(e))
                .map(GraphQLErrorAdapter::new)
                .collect(Collectors.toList());

        serverErrors.forEach(error -> {
            if (error instanceof Throwable) {
                System.out.println(error);
                log.error("Error executing query!", (Throwable) error);
            } else {
                log.error("Error executing query ({}): {}" + error.getClass().getSimpleName(), error.getMessage());
            }
        });

        return DevTool.listsTo1List(clientErrors, serverErrors);
    }

    private List<GraphQLError> filterGraphQLErrors(final List<GraphQLError> errors) {
        return errors;
    }

    protected boolean isClientError(final GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }
}