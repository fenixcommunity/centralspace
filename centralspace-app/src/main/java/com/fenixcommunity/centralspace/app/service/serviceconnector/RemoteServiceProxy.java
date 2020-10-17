package com.fenixcommunity.centralspace.app.service.serviceconnector;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.serviceconnector.credential.Credential;
import com.fenixcommunity.centralspace.app.service.serviceconnector.exception.UnsupportedRemoteServiceTypeException;
import com.fenixcommunity.centralspace.app.service.serviceconnector.google.GoogleRemoteService;
import com.fenixcommunity.centralspace.app.service.serviceconnector.microsoft.MicrosoftRemoteService;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class RemoteServiceProxy implements RemoteService {
    private final RemoteService remoteService;
    private final Validator validator = new ValidatorFactory().getInstance(NOT_NULL);

    public RemoteServiceProxy(final RemoteServiceType remoteServiceType) {
        validator.isValid(remoteServiceType);
        if (remoteServiceType == RemoteServiceType.GOOGLE) {
            remoteService = new GoogleRemoteService();
        } else if (remoteServiceType == RemoteServiceType.MICROSOFT) {
            remoteService = new MicrosoftRemoteService();
        }
        throw new UnsupportedRemoteServiceTypeException("Unsupported remote service type: " + remoteServiceType);
    }

    @Override
    public boolean testConnection(final Credential credential) {
        return remoteService.testConnection(credential);
    }
}
