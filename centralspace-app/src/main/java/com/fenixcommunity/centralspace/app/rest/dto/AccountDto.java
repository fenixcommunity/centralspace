package com.fenixcommunity.centralspace.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//todo ?
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class AccountDto {
//todo to all DTO, not works
    public String id;

    @NotNull
    @Min(value = 3, message = "login should not be less than 15")
    @Max(value = 15, message = "login should not be greater than 65")
    @ApiModelProperty(notes = "Account login")
    public String login;

    @NotNull
    @Email(regexp=".@.\\..*", message = "Email should be valid")
    public String mail;
}