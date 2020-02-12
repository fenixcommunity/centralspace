package com.fenixcommunity.centralspace.app.service.credential;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class GoogleCredentialProvider implements CredentialProvider<GoogleCredential> {

    @Override
    public GoogleCredential provideCredentialBasedOnAuthorizationCode(final String authorizationCode) {
        return new GoogleCredential();
    }
}