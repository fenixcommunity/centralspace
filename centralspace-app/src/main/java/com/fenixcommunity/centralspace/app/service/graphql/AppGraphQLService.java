package com.fenixcommunity.centralspace.app.service.graphql;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppGraphQLService {
    private final GraphQL graphQL;

    public ExecutionResult executeQuery(final String query, final String operationName, final Map<String, Object> variables) {
        return graphQL.execute(ExecutionInput.newExecutionInput()
                .query(query)
                .operationName(operationName)
                .variables(variables)
                .build());
    }
}
