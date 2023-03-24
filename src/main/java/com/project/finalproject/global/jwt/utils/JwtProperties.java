package com.project.finalproject.global.jwt.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Component
@ConstructorBinding
@Getter
@RequiredArgsConstructor
@ToString
public class JwtProperties {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

}
