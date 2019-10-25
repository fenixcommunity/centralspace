package com.fenixcommunity.centralspace.domain.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaAuditing
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.fenixcommunity.centralspace.domain")
@EnableJpaRepositories("com.fenixcommunity.centralspace.domain")
@EntityScan({"com.fenixcommunity.centralspace.domain"})
public class DomainConfig {

//    @Bean(name = "dataSource")      // 3
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }

}



