package com.fenixcommunity.centralspace.utilities.mail.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class MailContent {

    @NotBlank
    @Length(max = 20, min = 4)
    private String domain;

    @NotBlank
    @Email
    private String emailFrom;

    private MailRegistrationContent registrationContent;
}
