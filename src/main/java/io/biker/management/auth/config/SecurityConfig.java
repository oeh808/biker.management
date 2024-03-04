package io.biker.management.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.biker.management.auth.Roles;
import io.biker.management.auth.filter.JwtAuthFilter;
import io.biker.management.auth.service.UserRolesServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;

    // User Creation
    @Bean
    UserDetailsService userDetailsService() {
        return new UserRolesServiceImpl();
    }

    // Configuring HttpSecurity
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("customers", "/auth/generateToken",
                                "/swagger-ui/**", "/api-docs/**")
                        .permitAll())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/auth/backOffice/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/auth/bikers/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/auth/customers/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/auth/stores/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/auth/users/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/users/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/bikers/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/customers/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/stores/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/products/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/*/products/**").authenticated())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/orders/**").authenticated())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Password Encoding
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Role Hierarchy
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl r = new RoleHierarchyImpl();
        r.setHierarchy(Roles.ADMIN + " > " + Roles.CUSTOMER + " and " +
                Roles.ADMIN + " > " + Roles.BIKER + " and " +
                Roles.ADMIN + " > " + Roles.STORE + " and " +
                Roles.ADMIN + " > " + Roles.BACK_OFFICE);
        return r;
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    // FIXME: Remove before production
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
}
