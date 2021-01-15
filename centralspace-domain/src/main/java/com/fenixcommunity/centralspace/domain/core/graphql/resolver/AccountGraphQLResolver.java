package com.fenixcommunity.centralspace.domain.core.graphql.resolver;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fenixcommunity.centralspace.domain.core.graphql.dto.AccountGraphQLDto;
import com.fenixcommunity.centralspace.domain.core.graphql.dto.AddressGraphQLDto;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountGraphQLResolver implements GraphQLResolver<AccountGraphQLDto> {
//    public AddressGraphQLDto getAddress(final AccountGraphQLDto account) {
//        account
////        return new AddressGraphQLDto("Poland", "Cracvow");
//        return new AddressGraphQLDto(3L, "Poland", "Cracvow", List.of(account));
//    }
}
