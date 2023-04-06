package com.project.finalproject.global.jwt.utils;

import com.project.finalproject.global.exception.*;
import com.project.finalproject.login.dto.LoginResDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
@Getter
public class JwtFilter extends OncePerRequestFilter {

    /**
     * 허용할 url 세팅
     */
    private HashMap<String, String> permitUrl = new HashMap<>(){{
        put("/company/login","permit");
        put("/applicant/login","permit");
        put("/company/signup","permit");
        put("/applicant/signup","permit");
        put("/refresh","permit");
        put("/admin/login","permit");
        put("/reset","permit");
    }};

    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final JwtProperties jwtProperties;
    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, JwtProperties jwtProperties, StringRedisTemplate stringRedisTemplate) {
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public static JwtFilter of(JwtUtil jwtUtil,JwtProperties jwtProperties, StringRedisTemplate stringRedisTemplate) {
        return new JwtFilter(jwtUtil,jwtProperties,stringRedisTemplate);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String uri = request.getRequestURI();
        /**
         * Authorization 헤더값이 유효한지 검증
         */
        if(jwtUtil.checkHeader(authorizationHeader)){
            if(permitUrl.containsKey(uri) || uri.startsWith("/terms/")){
                filterChain.doFilter(request, response);
                return;
            }
            throw new UtilException(403, HttpStatus.FORBIDDEN, "INVALID");
        }
        String accessToken = jwtUtil.extractToken(authorizationHeader);

        /**
         * AccessToken이 블랙리스트(Redis)에 있는지 확인
         */
        if(stringRedisTemplate.hasKey(accessToken)){
            //login페이지로 redirect하라는 response
            if(jwtUtil.getRole(accessToken).equals("USER")){
                response.sendRedirect("/applicant/login");
                return;
            }
            else if(jwtUtil.getRole(accessToken).equals("COMPANY")){
                response.sendRedirect("/company/login");
                return;
            }
            else {
                response.sendRedirect("/admin/login");
                return;
            }
        }


        /**
         * AccessToken 기간이 유효한지 확인
         */
        if(jwtUtil.isAccessExpired(accessToken, jwtProperties.getSecretKey())){
            /**
             * AccessToken 유효하지 않을때, Refresh헤더를 확인
             */
            String refreshHeader = request.getHeader("REFRESH");
            if(jwtUtil.checkHeader(refreshHeader)){
                if(permitUrl.containsKey(request.getRequestURI()) || uri.startsWith("/terms/")){
                    filterChain.doFilter(request,response);
                    return;
                }
                throw new UtilException(403, HttpStatus.FORBIDDEN, "REFRESH");
            }
            String refreshToken = jwtUtil.extractToken(refreshHeader);

            /**
             * AccessToken 유효하지 않고, Refresh 토큰을 받았을 때, RefreshToken도 먼저 Redis에 있는지 확인
             */
            if(stringRedisTemplate.hasKey(refreshToken)){
                //login페이지로 redirect하라는 response
                if(jwtUtil.getRole(refreshToken).equals("USER")){
                    response.sendRedirect("/applicant/login");
                    return;
                }
                else if(jwtUtil.getRole(refreshToken).equals("COMPANY")){
                    response.sendRedirect("/company/login");
                    return;
                }
                else {
                    response.sendRedirect("/admin/login");
                    return;
                }
            }

            /**
             * Refresh토큰이 Redis에 없다면, 유효성 확인
             */
            if(jwtUtil.isRefreshExpired(refreshToken, jwtProperties.getSecretKey())){
                //login페이지로 redirect 하라는 response
                if(jwtUtil.getRole(refreshToken).equals("USER")){
                    response.sendRedirect("/applicant/login");
                    return;
                }
                else if(jwtUtil.getRole(refreshToken).equals("COMPANY")){
                    response.sendRedirect("/company/login");
                    return;
                }
                else {
                    response.sendRedirect("/admin/login");
                    return;
                }
            }
            else{
                //새로운 accesstoken 발급하는 예외처리
                throw new UtilException(403, HttpStatus.FORBIDDEN, refreshToken);
            }
        }
        LoginResDTO user = jwtUtil.getResDTO(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,
                "",
                user.getAuthorities()));

        filterChain.doFilter(request, response);
    }
}