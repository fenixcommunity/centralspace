package com.fenixcommunity.centralspace.domain.dto;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountDataDto {
    String login;
    String mail;
    Address address;
}
