package com.project.finalproject.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.global.dto.ResponseDTO;
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
        put("/company/checkemail","permit");
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
        String refreshHeader = request.getHeader("REFRESH");

        String uri = request.getRequestURI();
        if(permitUrl.containsKey(uri) || uri.startsWith("/terms/")){
            filterChain.doFilter(request, response);
            return;
        }

        if(tokenStr != null && !tokenStr.equalsIgnoreCase("null")){

            //black list 체크
            if(checkBlackList(tokenStr, response)) return;
            System.out.println(jwtUtil.isAccessExpired(tokenStr, jwtProperties.getSecretKey()));
            //access token 만료시간 체크
            if(jwtUtil.isAccessExpired(tokenStr, jwtProperties.getSecretKey())) {
                //refresh header 있는지 여부 확인
                if(refreshHeader == null){
                    throw new JwtUtilException(JwtUtilExceptionType.ACCESS_TOKEN_EXPIRATION_DATE);
                }
                logger.error("여기3");
                //refresh token
                String refresh = parseHeader(request, "REFRESH");
                if(checkBlackList(refresh, response)) return;
                if(jwtUtil.isRefreshExpired(refresh, jwtProperties.getSecretKey())){
                    throw new JwtUtilException(JwtUtilExceptionType.REFRESH_TOKEN_EXPIRATION_DATE);
                }
                ObjectMapper om = new ObjectMapper();
                String email = jwtUtil.getRefreshUserEmail(refresh);
                String role = jwtUtil.getRole(refresh);
                Long id = jwtUtil.getId(refresh);
                String validAccessToken = jwtUtil.createAccessToken(email,jwtProperties.getSecretKey(),role,id);
                ResponseDTO responseDTO = new ResponseDTO(200, true, validAccessToken,"New AccessToken");

                //(DTO -> json)
                String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO);
                response.getWriter().write(jsonStr);
                return;
            }
            System.out.println("들어오나?");
            LoginResDTO user = jwtUtil.getResDTO(request.getHeader(HttpHeaders.AUTHORIZATION));
            System.out.println(user.getRole());
            SecurityContextHolder.getContext()
                    .setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    "",
                                    user.getAuthorities()
                            )
                    );
            filterChain.doFilter(request,response);
            return;
        }

        throw new JwtUtilException(JwtUtilExceptionType.USER_ACCESS_TOKEN_UN_AUTHORIZED);
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
    public boolean checkBlackList(String token, HttpServletResponse response) throws IOException {
        if(stringRedisTemplate.hasKey(token)){
            //login페이지로 redirect하라는 response
            if(jwtUtil.getRole(token).equals("USER")){
                response.sendRedirect("/applicant/login");
                return true;
            }
            else if(jwtUtil.getRole(token).equals("COMPANY")){
                response.sendRedirect("/company/login");
                return true;
            }
            else {
                response.sendRedirect("/admin/login");
                return true;
            }
        }
        return false;
    }
}