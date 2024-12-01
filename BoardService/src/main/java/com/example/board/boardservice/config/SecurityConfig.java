package com.example.board.boardservice.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React 개발 서버
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 필요한 메서드만 허용
                    configuration.setAllowCredentials(true); // 세션 기반 인증
                    configuration.setMaxAge(7200L); // Preflight 요청 캐싱
                    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // 명시적으로 허용할 헤더
                    configuration.setExposedHeaders(List.of("Access", "Authorization")); // 노출할 헤더 추가
                    return configuration;
                }))
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 폼 로그인 비활성화
                .formLogin(form -> form.disable())
                // HTTP Basic 인증 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())
                // 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/api/auth/session").permitAll() // Swagger 허용
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup", "/api/auth/login", "/api/auth/logout").permitAll() // 회원가입/로그인 허용
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll() // 게시글 조회 허용
                        .requestMatchers(HttpMethod.POST, "/api/posts").authenticated() // 쓰기/수정/삭제 인증 필요
                        .requestMatchers(HttpMethod.PUT, "/api/posts").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/posts").authenticated()
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                // 세션 설정
                .sessionManagement(session -> session
                        .maximumSessions(1) // 한 사용자당 하나의 세션만 유지
                        .maxSessionsPreventsLogin(false) // 이전 세션 무효화
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
