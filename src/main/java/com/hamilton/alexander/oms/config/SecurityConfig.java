package com.hamilton.alexander.oms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // @formatter:off
        http
            .authorizeRequests(authz -> {
                    authz
                        .anyRequest().authenticated();
                })
            .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        
        return http.build();
    } // @formatter:on 

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("http://localhost:8081/auth/realms/oms");
    }
    
}

