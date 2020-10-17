package com.fenixcommunity.centralspace.app.service.serviceconnector.credential;

public interface Credential {
    String getAccessToken();

    String getRefreshToken();

    Long getExpirationTimeInMillis();

    String getTokenId();
}