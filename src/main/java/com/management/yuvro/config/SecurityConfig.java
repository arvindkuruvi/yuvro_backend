package com.management.yuvro.config;

//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        log.info("Configuring security filter chain");
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/v3/api-docs/**",
//                                "/v2/api-docs",
//                                "/webjars/**",
//                                "/swagger-resources/**",
//                                "/h2-console/**",
//                                "/rs/auth/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                ).oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter()
//                                )
//                        ));
//        log.info("Security filter chain configured");
//        return http.build();
//    }
//
//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter() {
//        log.info("Configuring JWT Authentication Converter");
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            log.info("Extracting roles from JWT: {}", jwt.getClaims());
//            // Extract realm roles
//            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
//            if (realmAccess != null && realmAccess.containsKey("roles")) {
//                log.info("Found realm roles in JWT");
//                List<String> roles = (List<String>) realmAccess.get("roles");
//                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
//            }
//            log.info("Authorities after realm roles extraction: {}", authorities);
//            // Extract client roles
//            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//            if (resourceAccess != null) {
//                String clientId = env.getProperty("security.oauth2.client-id", "default-client-id");
//                if (resourceAccess.containsKey(clientId)) {
//                    log.info("Found resource access in JWT");
//                    Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get(clientId);
//                    if (clientRoles.containsKey("roles")) {
//                        log.info("Found client roles in JWT");
//                        List<String> roles = (List<String>) clientRoles.get("roles");
//                        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
//                    }
//                }
//            }
//
//            return authorities;
//        });
//        log.info("JWT Authentication Converter configured");
//        return converter;
//    }
//}

//import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
//import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
//import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//@KeycloakConfiguration
//public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/admin/**").hasRole("admin")
//                .antMatchers("/api/faculty/**").hasRole("faculty")
//                .antMatchers("/api/student/**").hasRole("student")
//                .anyRequest().permitAll()
//                .and()
//                .oauth2Login();  // For Keycloak authentication flow
//    }
//}
//


//import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@EnableWebSecurity
//@KeycloakConfiguration
//public class SecurityConfig extends org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/admin/**").hasRole("admin")
//                .antMatchers("/api/faculty/**").hasRole("faculty")
//                .antMatchers("/api/student/**").hasRole("student")
//                .anyRequest().permitAll()
//                .and()
//                .oauth2Login(); // Keycloak login flow
//    }
//}


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_ENDPOINTS = {
            "/public/**",            // public endpoints
            "/health",               // health check
            "/swagger-ui/**",        // Swagger UI
            "/v3/api-docs/**",      // Swagger docs
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/swagger-resources/**",
            "/h2-console/**",
            "/rs/auth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring security filter chain");
        http
                .csrf(AbstractHttpConfigurer::disable) // 🔥 Disable CSRF for stateless JWT
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // ✅ For H2 console (if needed)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        log.info("Security filter chain configured");
        return http.build();
    }

    /**
     * Converts JWT roles claim into Spring Security authorities.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        log.info("Configuring JWT Authentication Converter");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtAuthenticationConverter());
        return converter;



//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Optional, depending on Keycloak setup
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
//
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            log.info("Extracting roles from JWT: {}", jwt.getClaims());
//            var roles = new java.util.ArrayList<>(grantedAuthoritiesConverter.convert(jwt));
//
//            // Optionally map other custom roles here, e.g., from "resource_access"
//            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//            if (resourceAccess != null && resourceAccess.containsKey("your-client-id")) {
//                Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("your-client-id");
//                List<String> clientRoles = (List<String>) clientAccess.get("roles");
//                clientRoles.forEach(role -> roles.add(new SimpleGrantedAuthority("ROLE_" + role)));
//            }
//
//            return roles;
//        });
//        return jwtConverter;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        log.info("Configuring security filter chain");
//
//       return http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/v3/api-docs/**",
//                                "/v2/api-docs",
//                                "/webjars/**",
//                                "/swagger-resources/**",
//                                "/h2-console/**",
//                                "/rs/auth/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                ).oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter()
//                                )
//                        ))
//                .build();
//    }
//
//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter() {
//        log.info("Configuring JWT Authentication Converter");
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            log.info("Extracting roles from JWT: {}", jwt.getClaims());
//            // Extract realm roles
//            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
//            if (realmAccess != null && realmAccess.containsKey("roles")) {
//                log.info("Found realm roles in JWT");
//                List<String> roles = (List<String>) realmAccess.get("roles");
//                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
//            }
//            log.info("Authorities after realm roles extraction: {}", authorities);
//            // Extract client roles (optional)
//            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//            if (resourceAccess != null && resourceAccess.containsKey("your-client-id")) {
//                log.info("Found resource access in JWT");
//                Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get("your-client-id");
//                if (clientRoles.containsKey("roles")) {
//                    log.info("Found client roles in JWT");
//                    List<String> roles = (List<String>) clientRoles.get("roles");
//                    roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
//                }
//            }
//
//            return authorities;
//        });
//        log.info("JWT Authentication Converter configured");
//        return converter;
//    }
}