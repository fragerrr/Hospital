package com.example.project.config;

import com.example.project.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig {

    private final UsersDetailsService usersDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UsersDetailsService usersDetailsService, PasswordEncoder passwordEncoder) {
        this.usersDetailsService = usersDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.exceptionHandling().accessDeniedPage("/403");

        http
                .authorizeHttpRequests()
                .requestMatchers("/auth/login", "/error", "/", "/static/**").permitAll()
                .anyRequest().hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(usersDetailsService)
                .passwordEncoder(passwordEncoder);
    }


}
