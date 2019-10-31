package com.fenixcommunity.centralspace.app.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace"})
//@EnableJpaRepositories("com.fenixcommunity.centralspace.domain.repository")
//@EntityScan({"com.fenixcommunity.centralspace.domain.model"})
@SpringBootApplication
public class CentralspaceApplicationConfig {
}
