package com.fenixcommunity.centralspace.app.configuration.security.autosecurity;

import com.fenixcommunity.centralspace.app.configuration.annotation.IgnoreDuringScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.*;
import static com.fenixcommunity.centralspace.utilities.common.DevTool.listsTo1Array;
import static com.fenixcommunity.centralspace.utilities.common.DevTool.mergeStringArrays;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;

@IgnoreDuringScan
@Configuration
@EnableWebSecurity
public class AutoSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] APP_AUTH_LIST = {
            "/account/**",
            "/doc/**",
            "/mail/**",
            "/password/**",
            "/register/**"
    };

    private static final String[] BASIC_AUTH_LIST = {
            "/resource/**"
    };

    private static final String[] NO_AUTH_LIST = {
            "/logger/**"
    };

    private static final String[] SWAGGER_AUTH_LIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //todo change!
        auth.inMemoryAuthentication()
                .withUser(BASIC.name())
                .password(passwordEncoder().encode(PASSWORD))
                .roles(listsTo1Array(BASIC.getRoles()))
                .and()
                .withUser(ADMIN.name())
                .password(passwordEncoder().encode(PASSWORD))
                .roles(listsTo1Array(ADMIN.getRoles()))
                .and()
                .withUser(SWAGGER.name())
                .password(passwordEncoder().encode(PASSWORD))
                .roles(listsTo1Array(SWAGGER.getRoles()));
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(mergeStringArrays(SWAGGER_AUTH_LIST)).hasRole(SWAGGER.name())
                .antMatchers(BASIC_AUTH_LIST).hasRole(BASIC.name())
                .antMatchers(APP_AUTH_LIST).hasRole(ADMIN.name())
                .antMatchers(NO_AUTH_LIST).permitAll()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}