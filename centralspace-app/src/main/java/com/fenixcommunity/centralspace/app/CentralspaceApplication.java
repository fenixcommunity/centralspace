package com.fenixcommunity.centralspace.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
//@ComponentScan({"com.fenixcommunity.centralspace.app"})
@EntityScan({"com.fenixcommunity.centralspace.domain"})
@EnableJpaRepositories({"com.fenixcommunity.centralspace.domain.repository"})
public class CentralspaceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CentralspaceApplication.class);
    }
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CentralspaceApplication.class, args);
    }
}

