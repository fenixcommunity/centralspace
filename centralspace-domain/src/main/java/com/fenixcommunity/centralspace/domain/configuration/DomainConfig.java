package com.fenixcommunity.centralspace.domain.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing // uwzględnia @PrePersist, @PreRemove
@ComponentScan({"com.fenixcommunity.centralspace.domain.core"})
@EnableJpaRepositories({"com.fenixcommunity.centralspace.domain.repository"})
@EntityScan({"com.fenixcommunity.centralspace.domain.model"})
@PropertySource(value = {"classpath:domain.properties"})
public class DomainConfig {

//    @Bean(name = "dataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }

}



