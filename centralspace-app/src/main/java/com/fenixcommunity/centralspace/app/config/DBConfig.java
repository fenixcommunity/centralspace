package com.fenixcommunity.centralspace.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource myDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(name = "MsDataSource")
//    @ConfigurationProperties(prefix="spring.ms.datasource")
//    public DataSource msDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
    @Bean
    @Autowired
    public JdbcTemplate jdbcmyTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }
//
//    @Bean(name = "jdbcmyNamedTemplate")
//    @Autowired
//    public NamedParameterJdbcTemplate jdbcmyNamedTemplate(@Qualifier("myDataSource") DataSource ds) {
//        return new NamedParameterJdbcTemplate(ds);
//    }
//
//    @Bean(name = "jdbcMsTemplate")
//    @Autowired
//    public JdbcTemplate jdbcMsTemplate(@Qualifier("MsDataSource") DataSource hanaDataSource) {
//        return new JdbcTemplate(MsDataSource);
//    }

}
