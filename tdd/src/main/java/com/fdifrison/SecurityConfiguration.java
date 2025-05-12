package com.fdifrison;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(
        request ->
            request.requestMatchers(HttpMethod.GET).permitAll()
                    .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                    .anyRequest().authenticated());
    http.httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }
}
