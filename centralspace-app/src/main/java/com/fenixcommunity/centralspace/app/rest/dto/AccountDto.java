package com.fenixcommunity.centralspace.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//todo ?
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {

    public String id;
    public String login;
    public String mail;
}