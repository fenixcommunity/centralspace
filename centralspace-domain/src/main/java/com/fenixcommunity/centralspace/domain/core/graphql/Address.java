package com.fenixcommunity.centralspace.domain.core.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Address {
    private String name;
    private Account account;
}
