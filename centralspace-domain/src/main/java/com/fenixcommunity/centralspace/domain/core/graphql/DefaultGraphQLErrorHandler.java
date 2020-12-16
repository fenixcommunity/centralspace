package com.fenixcommunity.centralspace.domain.core.graphql;

import java.util.List;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GenericGraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultGraphQLErrorHandler implements GraphQLErrorHandler {
    // ...

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        final List<GraphQLError> clientErrors = filterGraphQLErrors(errors);
        if (clientErrors.size() < errors.size()) {

            // Some errors were filtered out to hide implementation - put a generic error in place.
            clientErrors.add(new GenericGraphQLError("Internal Server Error(s) while executing query"));

            errors.stream()
                    .filter(error -> !isClientError(error))
                    .forEach(error -> {
                        if(error instanceof Throwable) {
                            System.out.println(error);
                            log.error("Error executing query!", (Throwable) error);
                        } else {
                            log.error("Error executing query ({}): {}" + error.getClass().getSimpleName(), error.getMessage());
                        }
                    });
        }

        return clientErrors;
    }

    private List<GraphQLError> filterGraphQLErrors(final List<GraphQLError> errors) {
        return errors;
    }

    protected boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }
}