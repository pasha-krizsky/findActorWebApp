package com.pasha.findactor.security;

import com.pasha.findactor.common.Urls;
import com.pasha.findactor.model.constants.UserProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * This configuration creates a Servlet Filter known as "springSecurityFilterChain"
 * which is responsible for all the security (protecting the application URLs, validating submitted
 * username and password, redirecting to the log in form, etc.).
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String USERNAME_PARAMETER = "ssoId";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String REMEMBER_ME_PARAMETER = "remember-me";

    private static final int TOKEN_VALIDITY_SECONDS = 86400;

    private static final String REMEMBER_ME_KEY = "remember-me";

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Service to provide RememberMe functionality.
     */
    @Autowired
    private PersistentTokenRepository tokenRepository;

    /**
     * Builds a JDBC based authentication by adding {@link UserDetailsService} implementation
     * and {@link DaoAuthenticationProvider} to builder.
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationSuccessHandler successHandler = createSuccessHandler();

        http.authorizeRequests()
                .antMatchers(
                        Urls.WORKSHEETS_DIRECTOR_URL,
                        Urls.OFFER_WORKSHEET_PATTERN,
                        Urls.DECLINE_WORKSHEET_DIRECTOR_PATTERN)
                .access("hasRole('DIRECTOR')")
                .antMatchers(
                        Urls.WORKSHEETS_AGENT_URL,
                        Urls.CASTING_WORKSHEET_PATTERN,
                        Urls.DECLINE_WORKSHEET_AGENT_PATTERN)
                .access("hasRole('AGENT')")
                .antMatchers(
                        Urls.WORKSHEETS_USER_URL,
                        Urls.SUBMIT_NEW_WORKSHEET_URL,
                        Urls.EDIT_WORKSHEET_PATTERN)
                .access("hasRole('USER')")
                .antMatchers(
                        Urls.DELETE_USER_URL_PATTERN,
                        Urls.EDIT_USER_URL_PATTERN,
                        Urls.LIST_USERS_URL)
                .access("hasRole('ADMIN')")
                .antMatchers(
                        Urls.VIEW_WORKSHEET_PATTERN)
                .access("hasRole('AGENT') or hasRole('DIRECTOR')")
                .antMatchers(
                        Urls.ROOT_URL,
                        Urls.WELCOME_URL)
                .access("hasRole('USER') or hasRole('ADMIN') or hasRole('AGENT') or hasRole('DIRECTOR')")
                .and().formLogin().loginPage(Urls.LOGIN_URL).successHandler(successHandler)
                .loginProcessingUrl(Urls.LOGIN_URL)
                .usernameParameter(USERNAME_PARAMETER).passwordParameter(PASSWORD_PARAMETER)
                .and().rememberMe().rememberMeParameter(REMEMBER_ME_PARAMETER)
                .tokenRepository(tokenRepository).tokenValiditySeconds(TOKEN_VALIDITY_SECONDS)
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage(Urls.ACCESS_DENIED_URL);
    }

    /**
     * Used to encode passwords. All passwords are encoded.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
        return new PersistentTokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService, tokenRepository);
    }

    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    private AuthenticationSuccessHandler createSuccessHandler() {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        return (request, response, authentication) -> {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String role = auth.getAuthorities().toString();

            String targetUrl = Urls.ROOT_URL;

            if (role.contains(UserProfileType.USER.getUserProfileType())) {
                targetUrl = Urls.WORKSHEETS_USER_URL;
            } else if (role.contains(UserProfileType.AGENT.getUserProfileType())) {
                targetUrl = Urls.WORKSHEETS_AGENT_URL;
            } else if (role.contains(UserProfileType.DIRECTOR.getUserProfileType())) {
                targetUrl = Urls.WORKSHEETS_DIRECTOR_URL;
            } else if (role.contains(UserProfileType.ADMIN.getUserProfileType())) {
                targetUrl = Urls.LIST_USERS_URL;
            }

            redirectStrategy.sendRedirect(request, response, targetUrl);
        };
    }
}
