package com.library.config;

import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(unauthorizedHandler))
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
//                            try {
//                                authorizationManagerRequestMatcherRegistry
//                                        .requestMatchers(HttpMethod.POST, POST_AUTH_WHITELIST).permitAll()
//                                        .requestMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
//                                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
//                                        .anyRequest()
//                                        .authenticated()
//                                        .and()
//                                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                            } catch (Exception e) {
//                                throw new ResourceNotFoundException(e.getMessage());
//                            }
//                        }
//                )
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(daoAuthenticationProvider()).build();
//    }
}
