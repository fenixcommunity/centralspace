package com.fenixcommunity.centralspace.app.service.serviceconnector.google;

import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;

import com.fenixcommunity.centralspace.app.service.serviceconnector.credential.Credential;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
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