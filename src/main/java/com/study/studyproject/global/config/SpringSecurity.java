package com.study.studyproject.global.config;

import com.study.studyproject.global.auth.UserDetailsImpl;
import com.study.studyproject.global.jwt.JwtAccessDeniedHandler;
import com.study.studyproject.global.jwt.JwtAuthenticationEntryPoint;
import com.study.studyproject.global.jwt.JwtFilter;
import com.study.studyproject.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SpringSecurity {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //제외될 url
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**"); //제외될 url
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtil); // JwtFilter를 빈으로 등록
    }


    private final CorsFilter filter;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilter(filter);
        http.csrf(AbstractHttpConfigurer::disable) //
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 생성x
                .formLogin(f -> f.disable())
                .httpBasic(h -> h.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
        ;


        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/board/member/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/reply/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/postLike/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
        );

        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }



}
