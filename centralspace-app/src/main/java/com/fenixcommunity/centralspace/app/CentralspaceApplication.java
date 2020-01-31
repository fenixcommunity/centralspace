package com.fenixcommunity.centralspace.app;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import(CentralspaceApplicationConfig.class)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CentralspaceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
//todo        application.profiles(Profiles.STANDALONE_PROFILE, Profiles.SWAGGER_ENABLED_PROFILE);
        return application.sources(CentralspaceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CentralspaceApplication.class, args);
    }
}

