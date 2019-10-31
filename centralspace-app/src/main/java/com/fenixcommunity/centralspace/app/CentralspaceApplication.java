package com.fenixcommunity.centralspace.app;

import com.fenixcommunity.centralspace.app.config.CentralspaceApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import(CentralspaceApplicationConfig.class)
public class CentralspaceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CentralspaceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CentralspaceApplication.class, args);
    }
}

