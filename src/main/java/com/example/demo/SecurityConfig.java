package com.example.demo;

import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.access.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.web.cors.*;

import java.util.*;

@EnableMethodSecurity(securedEnabled=true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final AccessDeniedHandler accessDeniedHandler;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final AuthenticationFailureHandler authenticationFailureHandler;
  private final LogoutSuccessHandler logoutSuccessHandler;
  private final PasswordEncoder passwordEncoder;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity config) throws Exception {
    config.cors(cors->cors.configurationSource(corsConfigurationSource()));
    config.csrf(csrf-> csrf.disable());
    config.formLogin(form->form.loginPage("/login").loginProcessingUrl("/login").successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler));
    config.logout(logout-> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));
    config.exceptionHandling(handler->handler.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint));
    return config.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));
    config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    config.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    src.registerCorsConfiguration("/**", config);
    return src;
  }
}
