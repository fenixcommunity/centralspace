package com.fenixcommunity.centralspace.app.utils.mapper;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class AccountDtoWithoutBuilder {
    private Long id;
    private String login;
    private String mail;
    private ZonedDateTime dataBaseConsentExpiredDate;
    private String passwordType;

    public void customizeLogin(String login) {
        this.login = login + "_custom";
    }
}