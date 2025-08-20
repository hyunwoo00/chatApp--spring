package com.chatapp.messenger;

import com.chatapp.messenger.handler.CustomOAuth2ExceptionHandler;
import com.chatapp.messenger.handler.CustomOAuth2SuccessHandler;
import com.chatapp.messenger.service.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2ExceptionHandler customOAuth2ExceptionHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().authenticated()

                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                //oauth2
                .oauth2Login(config -> config
                        .successHandler(customOAuth2SuccessHandler)
                        .failureHandler(customOAuth2ExceptionHandler)
                        .userInfoEndpoint(endpointConfig -> endpointConfig
                                .userService(customOAuth2UserService)
                        )
                );

        return http.build();
    }
}
