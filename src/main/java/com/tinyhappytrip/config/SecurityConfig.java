package com.tinyhappytrip.config;

import com.tinyhappytrip.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.tinyhappytrip.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.tinyhappytrip.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.tinyhappytrip.oauth2.service.CustomOAuth2UserService;
import com.tinyhappytrip.security.jwt.JwtAuthenticationFilter;
import com.tinyhappytrip.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize ->
//                        authorize
//                                .requestMatchers("/mail/**").permitAll()
//                                .requestMatchers("/users/validate").permitAll()
//                                .requestMatchers("/users/login").permitAll()
//                                .requestMatchers("/users/signup").permitAll()
//                                .requestMatchers("/users/**").permitAll()
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/**").hasAnyRole("USER", "ADMIN")
//                                .anyRequest().permitAll()
//                )
//                .oauth2Login(configure ->
//                        configure.authorizationEndpoint(config -> config.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
//                                .userInfoEndpoint(config -> config.userService(customOAuth2UserService))
//                                .successHandler(oAuth2AuthenticationSuccessHandler)
//                                .failureHandler(oAuth2AuthenticationFailureHandler)
//                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}