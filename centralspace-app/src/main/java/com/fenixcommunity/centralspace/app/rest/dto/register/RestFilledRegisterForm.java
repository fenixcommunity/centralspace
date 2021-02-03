package com.fenixcommunity.centralspace.app.rest.dto.register;

import java.time.ZonedDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fenixcommunity.centralspace.utilities.adnotation.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@NoArgsConstructor @AllArgsConstructor @Builder
public class RestFilledRegisterForm {
    @NotNull
    @JsonProperty
    @ApiModelProperty(example = "Mieczysław")
    public String firstName;

    @JsonProperty
    @ApiModelProperty(example = "Naruto")
    public String lastName;

    @JsonProperty
    @ApiModelProperty(example = "maxUser")
    public String username;

    @Email
    @JsonProperty
    @ApiModelProperty(example = "max@o2.pl")
    public String mail;

    @ValidPassword
    @ApiModelProperty(example = "password1212@oqBB")
    public String password;

    @ApiModelProperty(example = "2")
    public Long roleGroupId;

    @NotNull
    @JsonProperty
    public RestContactDetails contactDetails;

    @JsonProperty
    @ApiModelProperty(example = "www.o2.pl")
    public String registerURL;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty
    @ApiModelProperty(example = "2021-01-13T17:09:12.658+0100")
    public ZonedDateTime dateOfRequest;
}