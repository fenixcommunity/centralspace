package com.fenixcommunity.centralspace.app;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
import com.fenixcommunity.centralspace.app.configuration.profile.Profiles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import(CentralspaceApplicationConfig.class)
public class CentralspaceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.profiles(Profiles.STANDALONE_PROFILE, Profiles.SWAGGER_ENABLED_PROFILE);
        return application.sources(CentralspaceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CentralspaceApplication.class, args);
    }
}

