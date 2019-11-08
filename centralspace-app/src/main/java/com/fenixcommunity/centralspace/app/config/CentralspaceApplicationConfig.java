package com.fenixcommunity.centralspace.app.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace"})
//@EnableJpaRepositories("com.fenixcommunity.centralspace.domain.repository")
//@EntityScan({"com.fenixcommunity.centralspace.domain.model"})
@SpringBootApplication
public class CentralspaceApplicationConfig {
}
