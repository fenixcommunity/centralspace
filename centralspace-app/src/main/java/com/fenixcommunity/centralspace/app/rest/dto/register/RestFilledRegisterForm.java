package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestFilledRegisterForm {

    public String firstName;
    public String lastName;
    public String mail;
    //TODO opakuj EMAIL
    public String registerURL;

    //todo test
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public ZonedDateTime dateTime;
}
