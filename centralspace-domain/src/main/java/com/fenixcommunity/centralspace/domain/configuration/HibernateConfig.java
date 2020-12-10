package com.fenixcommunity.centralspace.domain.configuration;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.domain.core.interceptor.DomainInterceptorRegistration;
import com.fenixcommunity.centralspace.domain.core.interceptor.DomainInterceptor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class HibernateConfig extends HibernateJpaAutoConfiguration {

    @Bean
    DomainInterceptor domainInterceptor() {
        return new DomainInterceptor();
    }

    @Bean
    public DomainInterceptorRegistration appInterceptorRegistration() {
        return new DomainInterceptorRegistration(domainInterceptor());
    }
}