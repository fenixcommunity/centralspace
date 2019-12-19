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
    @Min(value = 3, message = "login should not be less than 3")
    @Max(value = 15, message = "login should not be greater than 15")
    @ApiModelProperty(notes = "Account login", required = true, example = "maxLogin")
    public String login;

    @NotNull
    @ApiModelProperty(notes = "Email of the account.", required = true, example = "max3112@o2.pl")
    @Email(regexp = ".@.\\..*", message = "Email should be valid")
    public String mail;
}