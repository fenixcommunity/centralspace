package com.fenixcommunity.centralspace.app.utils.mapper;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class AccountDtoWithoutBuilder {
    private Long id;
    private String idString;
    private String login;
    private String mail;
    private String passwordType;
    private ContactDetailsDto contactDetailsDto;
    private ZonedDateTime dataBaseConsentExpiredDate;
}