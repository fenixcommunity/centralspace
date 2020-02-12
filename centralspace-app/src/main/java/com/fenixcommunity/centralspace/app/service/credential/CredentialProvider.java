package com.fenixcommunity.centralspace.app.service.credential;

public interface CredentialProvider<T extends Credential> {
    T provideCredentialBasedOnAuthorizationCode(String authorizationCode);
}