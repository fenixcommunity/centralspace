package com.fenixcommunity.centralspace.domain.configuration;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = {"classpath:domain-test.yml"}, factory = YamlFetcher.class)
@EnableTransactionManagement
@EnableJpaAuditing // uwzględnia @PrePersist, @PreRemove
@ComponentScan({"com.fenixcommunity.centralspace.domain.core.listener"})
@EnableJpaRepositories({"com.fenixcommunity.centralspace.domain.repository.permanent"})
@EntityScan({"com.fenixcommunity.centralspace.domain.model.permanent"})
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DomainConfigForTest {
}
