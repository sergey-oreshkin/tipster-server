package com.home.tipster.security.config;

import com.home.tipster.security.service.CommonFailureEntryPoint;
import com.home.tipster.security.service.CustomAuthenticationFailureHandler;
import com.home.tipster.security.service.CustomAuthenticationSuccessHandler;
import com.home.tipster.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomOAuth2UserService oAuth2UserService;

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    private final CommonFailureEntryPoint failureEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .cors().configurationSource(request -> getCorsConfiguration())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(request -> request
                        .antMatchers("/", "/oauth2/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .authenticationEntryPoint(failureEntryPoint)
                )
                .oauth2Login(request -> request
                        .authorizationEndpoint(oauth -> oauth
                                .baseUri("/oauth2/authorize")
                        )
                        .redirectionEndpoint(oauth -> oauth
                                .baseUri("/oauth2/callback/*")
                        )
                        .userInfoEndpoint(oauth -> oauth
                                .userService(oAuth2UserService)
                        )
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                );
        return http.build();
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        return corsConfiguration;
    }
}
