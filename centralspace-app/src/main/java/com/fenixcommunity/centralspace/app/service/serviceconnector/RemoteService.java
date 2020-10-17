package com.fenixcommunity.centralspace.app.service.serviceconnector;

import com.fenixcommunity.centralspace.app.service.serviceconnector.credential.Credential;

public interface RemoteService {
    boolean testConnection(final Credential credential);
}
