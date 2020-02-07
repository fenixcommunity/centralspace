package com.fenixcommunity.centralspace.app.rest.dto;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

//todo ?
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @Builder @FieldDefaults(level = PRIVATE)
public class AccountDto {

    @NotNull
    private Long id;

    @NotNull
    @Min(value = 3, message = "login should not be less than 3")
    @Max(value = 15, message = "login should not be greater than 15")
    @ApiModelProperty(notes = "Account login", required = true, example = "maxLogin")
    private String login;

    @NotNull
    @ApiModelProperty(notes = "Email of the account.", required = true, example = "max3112@o2.pl")
    @Email(regexp = ".@.\\..*", message = "Email should be valid")
    private String mail;

    @JsonCreator
    public AccountDto(@JsonProperty("id") Long id,
                      @JsonProperty("login") String login,
                      @JsonProperty("mail") String mail) {
//        @NotNull -> only info for compiler,  requireNonNull(id) -> runtime
        this.id = id;
        this.login = login;
        this.mail = mail;
    }

}