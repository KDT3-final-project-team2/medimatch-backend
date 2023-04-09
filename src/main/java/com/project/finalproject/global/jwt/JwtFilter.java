package com.project.finalproject.global.jwt;

import com.project.finalproject.global.jwt.utils.JwtProperties;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.login.dto.LoginResDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
@Getter
@Slf4j
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
        put("/applicant/checkemail","permit");
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
        log.info("JWT filter run");

        String tokenStr = parseHeader(request, HttpHeaders.AUTHORIZATION);

        if(tokenStr != null && !tokenStr.equalsIgnoreCase("null")){
            //black list 체크
            checkBlackList(tokenStr);

            //access token 만료시간 체크
            if(jwtUtil.validExpired(tokenStr)) {
                //refresh token
                String refresh = parseHeader(request, "REFRESH");
                checkBlackList(refresh);
                if(jwtUtil.validExpired(refresh)){
                    throw new JwtUtilException(JwtUtilExceptionType.REFRESH_TOKEN_EXPIRATION_DATE);
                }
            }

            LoginResDTO user = jwtUtil.getResDTO(request.getHeader(HttpHeaders.AUTHORIZATION));
            SecurityContextHolder.getContext()
                    .setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    "",
                                    user.getAuthorities()
                            )
                    );

        }

        filterChain.doFilter(request,response);
    }

    //request header 파싱
    public String parseHeader(HttpServletRequest request, String type){
        String authorization = request.getHeader(type);

        //토큰 검증 후 파싱하기
        if(StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")){
            return authorization.replace("Bearer ","");
        }

        return null;
    }

    //redis에 해당 토큰이 있는지 검증 (아직 테스트 안해봄)
    public void checkBlackList(String token){
        if(JwtUtil.isRefreshExpired(token, jwtProperties.getSecretKey())){
            if(jwtUtil.getRole(token).equals("USER")){
                throw new JwtUtilException(JwtUtilExceptionType.USER_ACCESS_TOKEN_UN_AUTHORIZED);
            }
            else if(jwtUtil.getRole(token).equals("COMPANY")){
                throw new JwtUtilException(JwtUtilExceptionType.COMPANY_ACCESS_TOKEN_UN_AUTHORIZED);
            }
            else {
                throw new JwtUtilException(JwtUtilExceptionType.ADMIN_ACCESS_TOKEN_UN_AUTHORIZED);
            }
        }
    }
}