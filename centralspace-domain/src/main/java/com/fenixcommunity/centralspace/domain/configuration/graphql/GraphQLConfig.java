package com.fenixcommunity.centralspace.domain.configuration.graphql;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.coxautodev.graphql.tools.SchemaParser;
import com.coxautodev.graphql.tools.SchemaParserBuilder;
import com.fenixcommunity.centralspace.domain.core.graphql.resolver.AccountGraphQLResolver;
import com.fenixcommunity.centralspace.domain.core.graphql.resolver.AddressGraphQLResolver;
import com.fenixcommunity.centralspace.domain.core.graphql.mutation.AppGraphQLMutation;
import com.fenixcommunity.centralspace.domain.core.graphql.query.AppGraphQLQuery;
import com.fenixcommunity.centralspace.domain.core.graphql.scalar.ZonedDateTimeScalar;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResourceLoader;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class GraphQLConfig {
    private static final String GRAPHQL_SCHEMA_RESOURCE = "graphql/schema.graphqls";
    private final InternalResourceLoader internalResourceLoader;
/*  GraphQLResolver<T> to resolve complex types.
    GraphQLQueryResolver to define the operations of the root Query type.
    GraphQLMutationResolver to define the operations of the root Mutation type.
    GraphQLSubscriptionResolver to define the operations of the root Subscription type.*/
    private final AppGraphQLQuery appGraphQLQuery;
    private final AppGraphQLMutation appGraphQLMutation;
    private final AddressGraphQLResolver addressGraphQLResolver;
    private final AccountGraphQLResolver accountGraphQLResolver;

    @Bean
    @DependsOn("graphQLSchema")
    public GraphQL graphQL(final GraphQLSchema graphQLSchema) {
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    @Bean
    public GraphQLSchema graphQLSchema(final SchemaParser schemaParser) {
        return schemaParser.makeExecutableSchema();
    }

    @Bean
    public SchemaParser schemaParser() {
        final String graphqlSchema = internalResourceLoader.loadResourceAsStringByPath(GRAPHQL_SCHEMA_RESOURCE);
        return new SchemaParserBuilder()
                .schemaString(graphqlSchema)
                .resolvers(List.of(appGraphQLQuery, appGraphQLMutation, addressGraphQLResolver, accountGraphQLResolver))
                .scalars(new ZonedDateTimeScalar(), ExtendedScalars.Locale)
                .build();
    }
}
