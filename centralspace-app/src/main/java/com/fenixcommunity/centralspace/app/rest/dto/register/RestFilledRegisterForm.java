package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestFilledRegisterForm extends ResourceSupport {

    public String firstName;
    public String lastName;
    public String email;
    public String registerURL;
}
