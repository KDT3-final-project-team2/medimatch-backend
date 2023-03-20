package com.project.finalproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String getAccessUserEmail(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userEmail", String.class);
    }
//    getUserEmail("Access", token, secretKey); secretKey는 application.properties에 있는 Access 토큰 SecretKey

    public static boolean isAccessExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String createAccessToken(String userEmail, String secretKey, long expireTimemMs) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userEmail", userEmail);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimemMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String getRefreshUserEmail(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userEmail", String.class);
    }
//    getUserEmail("Access", token, secretKey); secretKey는 application.properties에 있는 Access 토큰 SecretKey

    public static boolean isRefreshExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String createRefreshToken(String userEmail, String secretKey, long expireTimemMs) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userEmail", userEmail);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimemMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}


