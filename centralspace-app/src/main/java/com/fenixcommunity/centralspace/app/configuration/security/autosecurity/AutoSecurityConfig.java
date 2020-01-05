package com.fenixcommunity.centralspace.app.configuration.security.autosecurity;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.*;
import static com.fenixcommunity.centralspace.utilities.common.DevTool.listsTo1Array;
import static com.fenixcommunity.centralspace.utilities.common.DevTool.mergeStringArrays;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static lombok.AccessLevel.PRIVATE;

@EnableWebSecurity // (debug = true)
@ComponentScan({"com.fenixcommunity.centralspace.app.service.security"})
//todo FieldDefaults private final??
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AutoSecurityConfig {

    private static final String API_PATH = "/api";

    @SuppressWarnings("SpringJavaAutowiringInspection")
    private DataSource dataSource;

    private static final String[] APP_AUTH_LIST = {
            API_PATH + "/account/**",
            API_PATH + "/doc/**",
            API_PATH + "/mail/**",
            API_PATH + "/password/**",
            API_PATH + "/register/**"
    };

    private static final String[] BASIC_AUTH_LIST = {
            API_PATH + "/resource/**"
    };

    private static final String[] NO_AUTH_LIST = {
            API_PATH + "/logger/**"
    };

    private static final String[] SWAGGER_AUTH_LIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    public AutoSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username=?")
                .withUser(User.withUsername(DB_USER.name())
                        .password(passwordEncoder().encode(PASSWORD))
                        .roles(listsTo1Array(DB_USER.getRoles())))
                .and()
                .inMemoryAuthentication()
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


    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher(API_PATH + "/**").authorizeRequests()
                    .antMatchers(BASIC_AUTH_LIST).hasRole(BASIC.name())
                    .antMatchers(APP_AUTH_LIST).hasRole(ADMIN.name())
                    .antMatchers(NO_AUTH_LIST).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
        }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers(mergeStringArrays(SWAGGER_AUTH_LIST)).hasRole(SWAGGER.name())
                    .antMatchers(NO_AUTH_LIST).permitAll() // or hasAnyRole
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .rememberMe()
                    .rememberMeCookieName("apicookie")
                    .tokenValiditySeconds(60 * 60)
                    .and()
                    //todo String to static final
                    .logout().logoutUrl("/logout");
//                 .deleteCookies("JSESSIONID");
//                 .loginPage("/login")
//                 .failureUrl("/login-error")
//                 .loginProcessingUrl("/security_check")
//                 .usernameParameter("username").passwordParameter("password")
//                 .permitAll();
        }


    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}