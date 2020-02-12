package com.fenixcommunity.centralspace.app.service.credential;

import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class GoogleCredential implements Credential {

    @Override
    public String getAccessToken() {
        return "";
    }

    @Override
    public String getRefreshToken() {
        return "";
    }

    @Override
    public Long getExpirationTimeInMillis() {
        //todo -> to TimeTool
        return ZonedDateTime.now().getLong(MILLI_OF_SECOND);
    }

    @Override
    public String getTokenId() {
        return "";
    }
}