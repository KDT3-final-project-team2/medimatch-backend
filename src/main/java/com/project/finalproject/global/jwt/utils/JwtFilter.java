package com.project.finalproject.global.jwt.utils;

import com.project.finalproject.login.dto.LoginResDTO;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Getter
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Builder
    public JwtFilter(JwtUtil jwtUtil, StringRedisTemplate stringRedisTemplate) {
        this.jwtUtil = jwtUtil;
        this.stringRedisTemplate = stringRedisTemplate;
    }

//    public static JwtFilter of(JwtUtil jwtUtil){
//        return new JwtFilter(jwtUtil, stringRedisTemplate);
//    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("dofilterInternal");

        if(!stringRedisTemplate.hasKey(authorizationHeader)){
            try{
                LoginResDTO user = jwtUtil.getResDTO(authorizationHeader);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,
                        "",
                        user.getAuthorities()));
            } catch (ExpiredJwtException e){
                logger.error("ExpiredJwtException : Token has been expired");
            } catch (Exception e){
                logger.error("Exception : There is no token");
            }
        }
        filterChain.doFilter(request, response);
    }
}