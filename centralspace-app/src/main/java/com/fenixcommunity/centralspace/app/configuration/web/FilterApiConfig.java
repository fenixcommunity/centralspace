package com.fenixcommunity.centralspace.app.configuration.web;

import static lombok.AccessLevel.PRIVATE;

import javax.servlet.Filter;

import com.fenixcommunity.centralspace.app.rest.filter.HeaderApiFilter;
import com.fenixcommunity.centralspace.app.rest.filter.RequestResponseLoggingFilter;
import com.fenixcommunity.centralspace.app.rest.filter.SessionFilter;
import com.fenixcommunity.centralspace.app.rest.filter.cache.CacheCookieApiFilter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace.app.rest.filter"})
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FilterApiConfig {

    @Bean
    public FilterRegistrationBean registerHeaderApiFilter() {
        return createFilterRegistration(new HeaderApiFilter(), "/public/*", 1);
    }

    @Bean
    public FilterRegistrationBean registerCacheCookieApiFilter() {
        return createFilterRegistration(new CacheCookieApiFilter(), "/account/*", 2);
    }

    @Bean
    public FilterRegistrationBean registerSessionFilter() {
        return createFilterRegistration(new SessionFilter(), "/*", 1);
    }

    @Bean
    public FilterRegistrationBean registerLoggingFilter() {
        return createFilterRegistration(new RequestResponseLoggingFilter(), "/account/*", 3);
    }

    private FilterRegistrationBean createFilterRegistration(Filter filter, String context, int order) {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.addUrlPatterns(context);
        registrationBean.setOrder(order);
//      registrationBean.addInitParameter("name", "value");
        return registrationBean;
    }
}
