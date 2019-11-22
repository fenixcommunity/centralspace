package com.fenixcommunity.centralspace.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;

//todo ?
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto extends ResourceSupport {

    public String accountId;
    public String login;
    public String email;
}