package com.fenixcommunity.centralspace.domain.configuration.graphql;

import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fenixcommunity.centralspace.domain.core.graphql.AddressResolver;
import com.fenixcommunity.centralspace.domain.core.graphql.GraphQLErrorAdapter;
import com.fenixcommunity.centralspace.domain.core.graphql.Mutation;
import com.fenixcommunity.centralspace.domain.core.graphql.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class GraphQLConfig {

    @Bean
    public AddressResolver authorResolver() {
        return new AddressResolver();
    }

    @Bean
    public Query query() {
        return new Query();
    }

    @Bean
    public Mutation mutation() {
        return new Mutation();
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(final List<GraphQLError> errors) {
                final List<GraphQLError> clientErrors = errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());

                final List<GraphQLError> serverErrors = errors.stream()
                        .filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new)
                        .collect(Collectors.toList());

                final List<GraphQLError> e = new ArrayList<>();
                e.addAll(clientErrors);
                e.addAll(serverErrors);
                return e;
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }
}
