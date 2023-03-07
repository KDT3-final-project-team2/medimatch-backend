package com.project.finalproject.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") //클라이언트 url
                .allowedHeaders("*") //모든 헤더
                .allowedMethods("GET","POST","PUT","DELETE")
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);

    }
}
