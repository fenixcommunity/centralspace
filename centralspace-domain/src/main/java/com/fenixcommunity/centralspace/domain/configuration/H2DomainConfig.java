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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
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
//@EnableJpaAuditing - disabled, required only one
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "h2EntityManagerFactory",
        transactionManagerRef = "h2TransactionManager",
        basePackages = {"com.fenixcommunity.centralspace.domain.repository.memory"})
@ComponentScan({"com.fenixcommunity.centralspace.domain.core"})
@EntityScan({"com.fenixcommunity.centralspace.domain.model.memory"})
@PropertySource(value = {"classpath:domain.properties"})
public class H2DomainConfig {

    @Value("classpath:/script/h2/scripts.sql")
    private Resource dataScript;

    @Bean(name = "h2DataSource")
    @ConfigurationProperties(prefix = "postgres.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "h2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("h2DataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.fenixcommunity.centralspace.domain")
                .persistenceUnit("h2")
                .build();
    }

    console

    @Bean(name = "h2TransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("h2EntityManagerFactory") EntityManagerFactory
                    entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @Order(2)
    public DataSourceInitializer dataSourceInitializer(
            final @Qualifier("h2DataSource") DataSource dataSource,
            @Value("classpath:/script/h2/initialization.sql") Resource initializationScript) {
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


