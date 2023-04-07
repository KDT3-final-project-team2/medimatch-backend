package com.project.finalproject.global.config;

import com.project.finalproject.global.jwt.utils.JwtExceptionFilter;
import com.project.finalproject.global.jwt.utils.JwtFilter;
import com.project.finalproject.global.jwt.utils.JwtProperties;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    /**
     * 토큰을 사용하지 않는 허용 url
     */
    String[] permitUrl = {
            "/company/login","/applicant/signup","/applicant/checkemail","/company/checkemail",
            "/applicant/login","/company/signup", "/refresh","/terms/**","/reset"};

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final StringRedisTemplate stringRedisTemplate;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .cors()//기본 cors 설정
                .and()
                .csrf().disable() //위조요청 방지 비활성화
                .formLogin().disable() //formLogin 인증 비활성화
                .httpBasic().disable() //httpBasic 인증 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http    .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers(permitUrl)
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();


        http
                .addFilterBefore(JwtFilter.of(jwtUtil, jwtProperties, stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(JwtExceptionFilter.of(jwtUtil, jwtProperties), JwtFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().mvcMatchers(permitUrl);
    }
}
