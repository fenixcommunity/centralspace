package com.fenixcommunity.centralspace.app.service.security.user;

import com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup;
import com.fenixcommunity.centralspace.utilities.adnotation.ValidPassword;
import lombok.Builder;
import lombok.Value;

@Value @Builder
public class CreateUserData {
    private final String username;
    private final String mail;

    @ValidPassword
    private final String password;
    private final SecurityUserGroup securityUserGroup;
    private final Long roleGroupId;

    private final String country;
    private final String phoneNumber;
}
