package com.project.finalproject.global.jwt.utils;

import com.project.finalproject.login.dto.LoginResDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 헤더로부터 LoginResDTO 생성
     */
    public LoginResDTO getResDTO(String authorizationHeader) {
        checkAuthorizationHeader(authorizationHeader);
        String token = "";
        Claims claims = null;
        try {
            token = extractToken(authorizationHeader);
            claims = parsingToken(token);
            return new LoginResDTO(claims);
        } catch (Exception e) {
            logger.error("토큰이 없습니다.(2)");
        }
        return null;
    }

    /**
     * 헤더값이 유효한지 검증
     */
    private boolean checkAuthorizationHeader(String header) {
        if (header == null || !header.startsWith(jwtProperties.getTokenPrefix())) {
            logger.error("토큰이 없습니다.(1)");
        }
        return true;
    }

    /**
     * Bearer 빼고 토큰값만 가져오는 메서드
     */
    public String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(jwtProperties.getTokenPrefix().length());
    }

    /**
     * Token 값을 Claims로 바꿔주는 메서드
     */
    public Claims parsingToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Access 토큰에서 Email 추출
     */
    public String getAccessUserEmail(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token)
                .getBody().get("userEmail", String.class);
    }

    /**
     * Access 토큰 만료 확인
     */
    public static boolean isAccessExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    /**
     * Access 토큰 생성
     */
    public String createAccessToken(String userEmail, String secretKey, String role) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userEmail", userEmail);
        claims.put("role", role);
        claims.put("issuer", jwtProperties.getIssuer());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Refresh 토큰에서 Email 추출
     */
    public String getRefreshUserEmail(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token)
                .getBody().get("userEmail", String.class);
    }

    /**
     * Refresh 토큰 만료 확인
     */
    public static boolean isRefreshExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    /**
     * Refresh 토큰 생성
     */
    public String createRefreshToken(String userEmail, String secretKey, String role) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userEmail", userEmail);
        claims.put("role", role);
        claims.put("issuer", jwtProperties.getIssuer());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(14).toMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
