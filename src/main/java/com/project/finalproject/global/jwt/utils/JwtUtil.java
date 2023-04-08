package com.project.finalproject.global.jwt.utils;

import com.project.finalproject.global.jwt.JwtUtilException;
import com.project.finalproject.global.jwt.JwtUtilExceptionType;
import com.project.finalproject.login.dto.LoginResDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 헤더로부터 LoginResDTO 생성
     */
    public LoginResDTO getResDTO(String authorizationHeader) {
        checkHeader(authorizationHeader);
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
    public boolean checkHeader(String header) {
        if (header == null || !header.startsWith(jwtProperties.getTokenPrefix())) {
            logger.error("없는 토큰 또는 잘못된 형식의 토큰입니다.");
            return true;
        }
        return false;
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
     * 토큰에서 role 추출
     */
    public String getRole(String token) {
        try{
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token)
                    .getBody().get("role", String.class);
        }catch (ExpiredJwtException e){
            throw new JwtUtilException(JwtUtilExceptionType.ACCESS_TOKEN_EXPIRATION_DATE);
        }

    }

    /**
     * Access 토큰 만료 확인
     */
    public static boolean isAccessExpired(String token, String secretKey) {
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getExpiration().before(new Date());
        }
        catch(ExpiredJwtException e){
            return false;
        }
    }

    //토큰 유효시간 검증, 5분 지나면 false, 안지나면 true
    public boolean validExpired(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        }catch (ExpiredJwtException e){
            logger.info("Expired JWT token.");
        }
        return false;
    }

    /**
     * Access 토큰 생성
     */
    public String createAccessToken(String userEmail, String secretKey, String role, Long id) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userEmail", userEmail);
        claims.put("role", role);
        claims.put("id", id);
        claims.put("issuer", jwtProperties.getIssuer());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis())) //테스트를 위해 1분으로 해놓음. 원랜 5분
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Refresh 토큰에서 Email 추출
     */
    public String getRefreshUserEmail(String token) {
        try{
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token)
                    .getBody().get("userEmail", String.class);
        }catch (ExpiredJwtException e){
            throw new JwtUtilException(JwtUtilExceptionType.ACCESS_TOKEN_EXPIRATION_DATE);
        }

    }

    /**
     * Refresh 토큰 만료 확인
     */
    public static boolean isRefreshExpired(String token, String secretKey) {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getExpiration().before(new Date());
            return false;
        }
        catch(Exception e){
            return true;
        }
    }

    /**
     * Refresh 토큰 생성
     */
    public String createRefreshToken(String userEmail, String secretKey, String role, Long id) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userEmail", userEmail);
        claims.put("role", role);
        claims.put("id", id);
        claims.put("issuer", jwtProperties.getIssuer());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(14).toMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 헤더에서 토큰을 추출하고 토큰으로 부터 Email, role을 추출해주는 메서드
     */
    public HashMap<String, String> allInOne(String authorizationHeader){

        HashMap<String, String> info = new HashMap<>();
        String token = authorizationHeader.substring(jwtProperties.getTokenPrefix().length());
        String email = getAccessUserEmail(token);
        String role = getRole(token);
        String id = getId(token).toString();

        info.put("email",email);
        info.put("role",role);
        info.put("id",id);
        return info;
    }

    /**
     * 토큰에서 id값 추출
     */
    public Long getId(String token) {
        try{
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token)
                    .getBody().get("id", Long.class);

        } catch (ExpiredJwtException e){
            throw new JwtUtilException(JwtUtilExceptionType.ACCESS_TOKEN_EXPIRATION_DATE);
        }
    }

    /**
     * 토큰에서 유효기간 일시 추출
     */
    public Date getExpiration(String token) {
        try{
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token)
                    .getBody().getExpiration();
        }catch (ExpiredJwtException e){
            throw new JwtUtilException(JwtUtilExceptionType.ACCESS_TOKEN_EXPIRATION_DATE);
        }

    }

}
