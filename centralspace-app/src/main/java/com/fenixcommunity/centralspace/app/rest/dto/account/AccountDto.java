package com.fenixcommunity.centralspace.app.rest.dto.account;

import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @FieldDefaults(level = PRIVATE)
public class AccountDto {
    //todo utils
    private static final String MAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull
    @Size(min = 1, max = 15, message = "login should not be less than 3 and not be greater than 15")
    @ApiModelProperty(notes = "Account login", required = true, example = "maxLogin")
    private String login;

    @NotNull
    @ApiModelProperty(notes = "Email of the account.", required = true, example = "max3112@o2.pl")
    @Email(regexp = MAIL_REGEX, message = "Email should be valid")
    private String mail;

    private PasswordType passwordType;
    private ContactDetailsDto contactDetailsDto;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") // 2016-06-23 09:07:21.205-07:00
    // or  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    private ZonedDateTime dataBaseConsentExpiredDate;

    @JsonCreator
    @Builder
    public AccountDto(@JsonProperty("id") Long id,
                      @JsonProperty("login") String login,
                      @JsonProperty("mail") String mail,
                      @JsonProperty("passwordType") PasswordType passwordType,
                      @JsonProperty("contactDetailsDto") ContactDetailsDto contactDetailsDto,
                      @JsonProperty("dataBaseConsentExpiredDate") ZonedDateTime dataBaseConsentExpiredDate) {
//        @NotNull -> only info for compiler, requireNonNull(id) -> runtime
        this.id = id;
        this.login = login;
        this.mail = mail;
        this.passwordType = passwordType;
        this.contactDetailsDto = contactDetailsDto;
    }

    public static class AccountDtoBuilder {

        public AccountDtoBuilder contactDetailsDtoFromAddress(Address address) {
            if (address != null) {
                this.contactDetailsDto = new ContactDetailsDto(address.getCountry(), null);
            }
            return this;
        }
    }
}