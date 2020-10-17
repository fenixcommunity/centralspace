package com.fenixcommunity.centralspace.app.service.serviceconnector.credential;

public interface CredentialProvider<T extends Credential> {
    T provideCredentialBasedOnAuthorizationCode(final String authorizationCode);
}