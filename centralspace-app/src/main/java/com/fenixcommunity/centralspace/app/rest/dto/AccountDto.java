package com.fenixcommunity.centralspace.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

//todo ?
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto extends RepresentationModel {

    public String id;
    public String login;
    public String email;
}