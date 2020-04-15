package com.fenixcommunity.centralspace.domain.configuration;


import static lombok.AccessLevel.PRIVATE;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import lombok.experimental.FieldDefaults;
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
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing // uwzględnia @PrePersist, @PreRemove
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager",
        basePackages = {"com.fenixcommunity.centralspace.domain.repository.mounted"})
@ComponentScan({"com.fenixcommunity.centralspace.domain.core"})
@EntityScan({"com.fenixcommunity.centralspace.domain.model.mounted"})
@PropertySource(value = {"classpath:domain.yml"}, factory = YamlFetcher.class)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PostgresDomainConfig {

    @Primary
    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "postgres.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(final EntityManagerFactoryBuilder builder,
                         @Qualifier("postgresDataSource") final DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.fenixcommunity.centralspace.domain")
                .persistenceUnit("postgres")
                .build();
    }

    @Primary
    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("postgresEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Primary
    @Bean(name = "postgresDataSourceInitializer")
    public DataSourceInitializer dataSourceInitializer
            (@Qualifier("postgresDataSource") final DataSource dataSource,
             @Value("classpath:/script/postgres/postgres_initialization.sql") final Resource initializationScript) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator(initializationScript));
        return initializer;
    }

    private DatabasePopulator databasePopulator(final Resource initializationScript) {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSeparator(ScriptUtils.EOF_STATEMENT_SEPARATOR); // ScriptUtils.executeSqlScript(connection ...
        populator.addScript(initializationScript);
        return populator;
    }

}



