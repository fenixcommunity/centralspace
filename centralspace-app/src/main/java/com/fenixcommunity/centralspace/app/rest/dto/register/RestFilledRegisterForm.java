package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestFilledRegisterForm extends RepresentationModel {

    public String firstName;
    public String lastName;
    public String email;
    //TODO opakuj EMAIL
    public String registerURL;
}
