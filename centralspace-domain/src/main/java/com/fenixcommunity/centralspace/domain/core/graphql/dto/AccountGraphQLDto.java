package com.fenixcommunity.centralspace.domain.core.graphql.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @FieldDefaults(level = PRIVATE)
public class AccountGraphQLDto {
    //todo utils
    private static final String MAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    @JMap
    private Long id;

    @NotNull
    @Size(min = 1, max = 15, message = "login should not be less than 3 and not be greater than 15")
    @JMap
    private String login;

    @NotNull
    @Email(regexp = MAIL_REGEX, message = "Email should be valid")
    @JMap
    private String mail;

    @JMap("nip")
    private String identifier;
    private PasswordType passwordType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") // 2021-01-13T17:09:12.658+0100
    // or  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    @JMap
    private ZonedDateTime dataBaseConsentExpiredDate;

    @JsonCreator
    @Builder
    public AccountGraphQLDto(@JsonProperty("id") Long id,
                             @JsonProperty("login") String login,
                             @JsonProperty("mail") String mail,
                             @JsonProperty("passwordType") PasswordType passwordType,
                             @JsonProperty("dataBaseConsentExpiredDate") String dataBaseConsentExpiredDate) {
        this.id = id;
        this.login = login;
        this.mail = mail;
        this.passwordType = passwordType;
        this.dataBaseConsentExpiredDate = ZonedDateTime.parse(dataBaseConsentExpiredDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    }
}