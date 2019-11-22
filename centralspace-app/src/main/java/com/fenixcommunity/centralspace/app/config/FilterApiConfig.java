package com.fenixcommunity.centralspace.app.config;

import com.fenixcommunity.centralspace.app.rest.filter.HeaderApiFilter;
import com.fenixcommunity.centralspace.app.rest.filter.RequestResponseLoggingFilter;
import com.fenixcommunity.centralspace.app.rest.filter.cache.CacheCookieApiFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
@ComponentScan({"com.fenixcommunity.centralspace.app.rest.filter"})
public class FilterApiConfig {

    @Bean
    public FilterRegistrationBean registerHeaderApiFilter() {
        return createFilterRegistration(new HeaderApiFilter(), "/account/*", 1);
    }

    @Bean
    public FilterRegistrationBean registerCacheCookieApiFilter() {
        return createFilterRegistration(new CacheCookieApiFilter(), "/account/*", 2);
    }

    @Bean
    public FilterRegistrationBean registerLoggingFilter() {
        return createFilterRegistration(new RequestResponseLoggingFilter(), "/account/*", 3);
    }

    private FilterRegistrationBean createFilterRegistration(Filter filter, String context, int order) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.addUrlPatterns(context);
        registrationBean.setOrder(order);
//      registrationBean.addInitParameter("name", "value");
        return registrationBean;
    }
}
