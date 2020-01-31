package com.fenixcommunity.centralspace.utilities.mail.properties;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter @FieldDefaults(level = PRIVATE)
public class MailRegistrationContent {

    public static final String ACCOUNT_TOKEN = "account_token";

    public MailRegistrationContent(@NotBlank String fullUrl, @NotBlank String url, @NotBlank String domainId) {
        this.fullUrl = fullUrl;
        this.url = url;
        this.domainId = domainId;
    }

    @NotBlank
    private final String fullUrl;
    @NotBlank
    private final String url;
    @NotBlank
    private final String domainId;
    private String accountToken;
}
