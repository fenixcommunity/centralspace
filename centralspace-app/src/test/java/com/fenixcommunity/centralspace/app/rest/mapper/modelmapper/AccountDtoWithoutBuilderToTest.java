package com.fenixcommunity.centralspace.app.rest.mapper.modelmapper;

import java.time.ZonedDateTime;

import com.fenixcommunity.centralspace.app.rest.dto.account.ContactDetailsDto;
import lombok.Data;

@Data
class AccountDtoWithoutBuilderToTest {
    private Long id;
    private String idString;
    private String login;
    private String mail;
    private String passwordType;
    private ContactDetailsDto contactDetailsDto;
    private ZonedDateTime dataBaseConsentExpiredDate;
}