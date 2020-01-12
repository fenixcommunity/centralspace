package com.fenixcommunity.centralspace.domain.configuration;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing // uwzględnia @PrePersist, @PreRemove
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager",
        basePackages = {"com.fenixcommunity.centralspace.domain.repository.mounted"})
@ComponentScan({"com.fenixcommunity.centralspace.domain.core"})
@EntityScan({"com.fenixcommunity.centralspace.domain.model.mounted"})
@PropertySource(value = {"classpath:domain.properties"})
public class PostgresDomainConfig {

    @Value("classpath:/script/postgres/security_jdbc_users.sql")
    private Resource dataScript;

    @Primary
    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "postgres.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.fenixcommunity.centralspace.domain")
                .persistenceUnit("postgres")
                .build();
    }

    @Primary
    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory
                    entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @Order(1)
    public DataSourceInitializer dataSourceInitializer(final @Qualifier("postgresDataSource") DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dataScript);
        return populator;
    }

}



