package com.fenixcommunity.centralspace.app.service.credential;

interface Credential {
    String getAccessToken();

    String getRefreshToken();

    Long getExpirationTimeInMillis();

    String getTokenId();
}