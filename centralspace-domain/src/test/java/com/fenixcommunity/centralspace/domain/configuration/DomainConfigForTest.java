package com.fenixcommunity.centralspace.domain.configuration;

import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@PropertySource(value = {"classpath:domain-test.properties"})
@EnableTransactionManagement
@EnableJpaAuditing // uwzględnia @PrePersist, @PreRemove
@ComponentScan({"com.fenixcommunity.centralspace.domain.core"})
@EnableJpaRepositories({"com.fenixcommunity.centralspace.domain.repository"})
@EntityScan({"com.fenixcommunity.centralspace.domain.model"})
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DomainConfigForTest {
}
