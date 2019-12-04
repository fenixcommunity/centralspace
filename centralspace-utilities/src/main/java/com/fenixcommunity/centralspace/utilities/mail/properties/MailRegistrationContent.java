package com.fenixcommunity.centralspace.utilities.mail.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MailRegistrationContent {

    public MailRegistrationContent(@NotBlank String fullUrl, @NotBlank String url, @NotBlank String domainId) {
        this.fullUrl = fullUrl;
        this.url = url;
        this.domainId = domainId;
    }

    @NotBlank
    private String fullUrl;
    @NotBlank
    private String url;
    @NotBlank
    private String domainId;
    private String accountToken;
}
