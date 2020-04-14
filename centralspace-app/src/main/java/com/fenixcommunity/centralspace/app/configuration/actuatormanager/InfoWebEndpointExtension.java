package com.fenixcommunity.centralspace.app.configuration.actuatormanager;

import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

@Component
@EndpointWebExtension(endpoint = InfoEndpoint.class)
public class InfoWebEndpointExtension implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("App info", "Centralspace App");
    }
}
