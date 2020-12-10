package com.fenixcommunity.centralspace.domain.core.interceptor;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Interceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class DomainInterceptorRegistration implements HibernatePropertiesCustomizer {

    @Qualifier("domainInterceptor")
    private final Interceptor domainInterceptor;

    @Override
    public void customize(final Map<String, Object> vendorProperties) {
        vendorProperties.put("hibernate.session_factory.interceptor", domainInterceptor);
    }
}