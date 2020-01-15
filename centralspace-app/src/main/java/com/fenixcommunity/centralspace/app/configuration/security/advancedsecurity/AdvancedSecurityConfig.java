package com.fenixcommunity.centralspace.app.configuration.security.advancedsecurity;

import com.fenixcommunity.centralspace.app.service.security.advanced.InMemoryUserDetailsService;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.utilities.common.DevTool.mergeStringArrays;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

//@Configuration
//@EnableWebSecurity
//@ComponentScan({"com.fenixcommunity.centralspace.app.service.security"})
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Deprecated // info: alternative to auto security config
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AdvancedSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/public/**")
    );
    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);
    private final AbstractUserDetailsAuthenticationProvider provider;
    private final InMemoryUserDetailsService userService;

    AdvancedSecurityConfig(final AbstractUserDetailsAuthenticationProvider provider, InMemoryUserDetailsService userService) {
        super();
        this.provider = requireNonNull(provider);
        //todo requireNonNull all place
        this.userService = requireNonNull(userService);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    private static final String[] BASIC_AUTH_LIST = {
            "/logger/**"
    };

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .userDetailsService(userService)
//              .and() custom like above or for example inMemoryAuthentication
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
//                .requestMatchers(PROTECTED_URLS)
//                .authenticated()
                .antMatchers(mergeStringArrays(BASIC_AUTH_LIST)).hasRole(BASIC.name())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    @Bean
    TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
        final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }


    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

    @Bean
    FilterRegistrationBean disableAutoRegistration(final TokenAuthenticationFilter filter) {
        final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(FORBIDDEN);
    }

    //    if .rememberMe().rememberMeServices(rememberMeServices()).key("password");
//    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("password", userService);
        rememberMeServices.setCookieName("cookieName");
        rememberMeServices.setParameter("rememberMe");
        return rememberMeServices;
    }
}
