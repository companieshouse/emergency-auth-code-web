package uk.gov.companieshouse.web.emergencyauthcodeweb.security;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uk.gov.companieshouse.auth.filter.HijackFilter;
import uk.gov.companieshouse.auth.filter.UserAuthFilter;
import uk.gov.companieshouse.session.handler.SessionHandler;

@EnableWebSecurity
public class WebSecurity {

    @Bean
    @Order(1)
    public SecurityFilterChain authCodeSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/auth-code-requests/start", "/auth-code-requests/accessibility-statement")
            .addFilterBefore(new SessionHandler(), BasicAuthenticationFilter.class)
            .addFilterBefore(new HijackFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain eacWebSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/auth-code-requests/**")
            .addFilterBefore(new SessionHandler(), BasicAuthenticationFilter.class)
            .addFilterBefore(new HijackFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new UserAuthFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }
}

