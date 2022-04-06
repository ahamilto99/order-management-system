package com.hamilton.alexander.oms.config;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamilton.alexander.oms.config.exception.ErrorMessage;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

     private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // @formatter:off
        http
            .authorizeRequests(authz -> {
                    authz
                        .mvcMatchers("/employees/**").hasAnyAuthority("SCOPE_manager")
                        .anyRequest().authenticated();
                })
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler())
            .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        
        return http.build();
    } // @formatter:on 

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {

            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                    throws IOException, ServletException {

                HttpStatus status = HttpStatus.UNAUTHORIZED;

                response.setStatus(status.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ObjectMapper objMapper = new ObjectMapper();
                var responseBody = objMapper.writeValueAsString(new ErrorMessage(Instant.now().toString(), status.value(),
                        status.getReasonPhrase(), "Valid bearer token required", HtmlUtils.htmlEscape(request.getRequestURI())));

                response.getWriter().write(responseBody);
            }

        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
                    throws IOException, ServletException {
                JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                if (jwt != null) {
                    LOG.warn("User '%s' attempted to access the protected URI: %s"
                            .formatted(jwt.getToken().getClaimAsString("preferred_username"), HtmlUtils.htmlEscape(request.getRequestURI())));
                }

                HttpStatus status = HttpStatus.FORBIDDEN;

                response.setStatus(status.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ObjectMapper objectMapper = new ObjectMapper();
                var responseBody = objectMapper.writeValueAsString(new ErrorMessage(Instant.now().toString(), status.value(),
                        status.getReasonPhrase(), "Insufficient privileges", request.getRequestURI()));

                response.getWriter().write(responseBody);
            };

        };
    }

}
