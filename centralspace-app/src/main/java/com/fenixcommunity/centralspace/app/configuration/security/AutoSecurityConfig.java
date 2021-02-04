package com.fenixcommunity.centralspace.app.configuration.security;

import static com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup.GUEST_USER;
import static com.fenixcommunity.centralspace.utilities.common.CollectionTool.mergeStringArrays;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD_GUEST;
import static lombok.AccessLevel.PRIVATE;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import com.fenixcommunity.centralspace.app.configuration.security.handler.AppAuthenticationFailureHandler;
import com.fenixcommunity.centralspace.app.configuration.security.handler.AppAuthenticationSuccessHandler;
import com.fenixcommunity.centralspace.app.configuration.security.handler.AppLogoutSuccessHandler;
import com.fenixcommunity.centralspace.app.service.security.helper.LoginAttemptService;
import com.fenixcommunity.centralspace.utilities.common.StringTool;
import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@EnableWebSecurity // (debug = true)
//@EnableWebFluxSecurity todo https://www.baeldung.com/spring-security-5-reactive
@ComponentScan({"com.fenixcommunity.centralspace.app.service.security"})
@FieldDefaults(level = PRIVATE, makeFinal = true)
@PropertySource(value = {"classpath:security.yml"}, factory = YamlFetcher.class)
public abstract class AutoSecurityConfig {
/*  TODO   tokeny -> przychodzi token, przydzielamy token do usera ale takze weryfikujemy czy taki token wystawilismy
*   TODO jwt cracker -> > 18 znaków zmieszanych
*   TODO  httpOnly w cookie ustaw!
* */

    private static final String API_PATH = "/api";
    public static final String COOKIE_SESSION_ID = "JSESSIONID";
    private static final String REMEMBER_ME_COOKIE = "remembermecookie";
    private static final int SESSION_TIMEOUT_SECONDS = 60 * 10; //todo to properties
    private static final int TOKEN_VALIDITY_SECONDS = 60 * 45;

    private static final String[] ADMIN_API_AUTH_LIST = {
            API_PATH + "/account/**",
            API_PATH + "/aws/**",
            API_PATH + "/account-flux/**",
            API_PATH + "/doc/**",
            API_PATH + "/mail/**",
            API_PATH + "/password/**",
            API_PATH + "/register/**",
            API_PATH + "/role-creator/**",
            API_PATH + "/metrics/**",
            API_PATH + "/async/**",
            API_PATH + "/customization/**",
            API_PATH + "/sms-sender/**",
            API_PATH + "/app-control/**",
            API_PATH + "/batch/**",
            API_PATH + "/features/**",
            API_PATH + "/graphql/**",
    };
    private static final String[] BASIC_API_AUTH_LIST = {
            API_PATH + "/resource-cache/**"
    };
    private static final String[] FLUX_API_AUTH_LIST = {
            API_PATH + "/account-flux/**"
    };
    private static final String[] NO_AUTH_API_LIST = {
            API_PATH + "/logger/basic-info",
            API_PATH + "/cross/**"
    };
    //FORM
    private static final String[] ADMIN_FORM_AUTH_LIST = {
            "/h2-console/**",
            "/actuator/**",
            "/prometheus/**",
            "/graphiql/**"
    };
    private static final String[] NO_AUTH_FORM_LIST = {
            API_PATH + "/logger/test",
            "/public/**"
    };
    private static final String[] SWAGGER_AUTH_LIST = {
            "/swagger",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    private final DataSource postgresDataSource;

    public AutoSecurityConfig(final @Qualifier("postgresDataSource") DataSource postgresDataSource) {
        this.postgresDataSource = postgresDataSource;
    }

    @Bean
    public DaoAuthenticationProvider authProvider(final UserDetailsService userDetailsService,
                                                  final PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Autowired
    public void configureGlobal(final DaoAuthenticationProvider authProvider,
                                final AuthenticationManagerBuilder auth,
                                final PasswordEncoder passwordEncoder,
                                final @Value("${authorizationQuery.getUserQuery}") String getUserQuery,
                                final @Value("${authorizationQuery.getAuthoritiesQuery}") String getAuthoritiesQuery,
                                final @Value("${authorizationQuery.getGroupAuthoritiesByUsernameQuery}") String getGroupAuthoritiesByUsernameQuery) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(postgresDataSource)
                .usersByUsernameQuery(getUserQuery)
                .authoritiesByUsernameQuery(getAuthoritiesQuery)
                .groupAuthoritiesByUsername(getGroupAuthoritiesByUsernameQuery)
                .passwordEncoder(passwordEncoder);
        // to testing authenticationSuccessListener
        auth.eraseCredentials(false);
        auth.inMemoryAuthentication()
                .withUser(GUEST_USER.name())
                .password(passwordEncoder.encode(PASSWORD_GUEST))
                .authorities(GUEST_USER.getAuthorities().toArray(String[]::new));

        auth.authenticationProvider(authProvider);
    }

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final AdminServerProperties adminServer;
        private final DataSource postgresDataSource;
        private final DataSource h2DataSource;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(final AdminServerProperties adminServer,
                                                     final @Qualifier("postgresDataSource") DataSource postgresDataSource,
                                                     final @Qualifier("h2DataSource") DataSource h2DataSource) {
            this.adminServer = adminServer;
            this.postgresDataSource = postgresDataSource;
            this.h2DataSource = h2DataSource;
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http
                    .exceptionHandling()
//                  .authenticationEntryPoint(appBasicAuthenticationEntryPoint())
                    .and()
                    //API
//                  .antMatcher(API_PATH + "/**").authorizeRequests()
                    .authorizeRequests()
                    .antMatchers(BASIC_API_AUTH_LIST).hasAuthority("ROLE_BASIC")
                    .antMatchers(ADMIN_API_AUTH_LIST).hasAuthority("ROLE_ADMIN")
                    .antMatchers(FLUX_API_AUTH_LIST).hasAuthority("ROLE_FLUX_GETTER")
                    .antMatchers(this.adminServer.path("/assets/**")).permitAll()
                    .antMatchers(this.adminServer.path("/login")).permitAll()
                    .antMatchers(NO_AUTH_API_LIST).permitAll()
                    //FORM
                    .antMatchers(mergeStringArrays(SWAGGER_AUTH_LIST)).hasAuthority("ROLE_SWAGGER")
                    .antMatchers(mergeStringArrays(ADMIN_FORM_AUTH_LIST)).hasAuthority("ROLE_ADMIN")
                    .antMatchers(NO_AUTH_FORM_LIST).permitAll()
                    .anyRequest().hasAuthority("ROLE_SWAGGER")
                    .and()
                    .cors()//.configurationSource(corsConfigurationSource())
                    .and()
                    .csrf().disable()
//                    .csrf()
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                    .ignoringRequestMatchers(
//                            new AntPathRequestMatcher(this.adminServer.path("/instances"), HttpMethod.POST.toString()),
//                            new AntPathRequestMatcher(this.adminServer.path("/instances/*"), HttpMethod.DELETE.toString()),
//                            new AntPathRequestMatcher(this.adminServer.path("/actuator/**")))
//                    .and()
                    .httpBasic()
                    .and()
                    .headers()
                    .frameOptions().sameOrigin()
                    .and()
                    .formLogin().permitAll()
//                   .loginPage("/login") TODO will be provided by React
//                   .failureUrl("/login-error") TODO will be provided by React
                    .usernameParameter("username").passwordParameter("password")
                    .loginProcessingUrl("/public/users/signin") // custom post request url in React form
                    .usernameParameter("username").passwordParameter("password")
                    .successHandler(appAuthenticationSuccessHandler(loginAttemptService(), passwordEncoder()))
                    .failureHandler(appAuthenticationFailureHandler(loginAttemptService()))
                    .and()
                    .logout().logoutUrl("/public/users/signout") //TODO will be provided by React
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies(REMEMBER_ME_COOKIE)
                    .and()
                    .rememberMe().key(StringTool.generateSecureToken())
                    .rememberMeCookieName(REMEMBER_ME_COOKIE)
                    .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS)
                    .tokenRepository(tokenRepository())
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // NEVER - if full stateless app
                    .maximumSessions(2)
                    .expiredUrl("/login?expired");
                 /*
                 .portMapper().http(9090).mapsTo(9443).http(80).mapsTo(443);
                 .deleteCookies("JSESSIONID");
                  */
        }

        @Bean
        public JdbcUserDetailsManager jdbcUserDetailsManager(final @Value("${authorizationQuery.getUserQuery}") String getUserQuery,
                                                             final @Value("${authorizationQuery.getAuthoritiesQuery}") String getAuthoritiesQuery,
                                                             final @Value("${authorizationQuery.getGroupAuthoritiesByUsernameQuery}") String getGroupAuthoritiesByUsernameQuery,
                                                             final @Value("${authorizationQuery.getUserExistsQuery}") String getUserExistsQuery) {
            final JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(postgresDataSource);
            jdbcUserDetailsManager.setUsersByUsernameQuery(getUserQuery);
            jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(getAuthoritiesQuery);
            jdbcUserDetailsManager.setGroupAuthoritiesByUsernameQuery(getGroupAuthoritiesByUsernameQuery);
            jdbcUserDetailsManager.setUserExistsSql(getUserExistsQuery);
            return jdbcUserDetailsManager;
        }

        @Bean
        public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
            return new SecurityEvaluationContextExtension();
        }

        @Bean
        AuthenticationSuccessHandler appAuthenticationSuccessHandler(final LoginAttemptService loginAttemptService, final PasswordEncoder encoder) {
            return new AppAuthenticationSuccessHandler(loginAttemptService, encoder, SESSION_TIMEOUT_SECONDS, "/app/swagger-ui.html");
        }

        @Bean
        AuthenticationFailureHandler appAuthenticationFailureHandler(final LoginAttemptService loginAttemptService) {
            return new AppAuthenticationFailureHandler(loginAttemptService);
        }

        @Bean
        public LogoutSuccessHandler logoutSuccessHandler() {
            return new AppLogoutSuccessHandler();
        }

        @Bean
        JdbcTokenRepositoryImpl tokenRepository() {
            final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
            tokenRepository.setCreateTableOnStartup(false);
            tokenRepository.setDataSource(h2DataSource);
            return tokenRepository;
        }

        @Bean
        HttpSessionEventPublisher httpSessionEventPublisher() {
            return new HttpSessionEventPublisher();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            // we don't need salt for user password
            final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            final Map<String, PasswordEncoder> encoders = new HashMap<>();
            encoders.put("bcrypt", bCryptPasswordEncoder);
            encoders.put("scrypt", new SCryptPasswordEncoder());

            final DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(
                    "bcrypt", encoders);
            passwordEncoder.setDefaultPasswordEncoderForMatches(bCryptPasswordEncoder);

            return passwordEncoder;
        }

        @Bean
        LoginAttemptService loginAttemptService() {
            return new LoginAttemptService();
        }
    }
}