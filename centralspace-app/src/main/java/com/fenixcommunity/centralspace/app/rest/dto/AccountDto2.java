package com.fenixcommunity.centralspace.app.rest.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AccountDto2 {
    private Long id;
    private String login;
    private String mail;
}
