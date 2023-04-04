package com.project.finalproject.global.jwt.utils;

import com.project.finalproject.global.exception.UtilException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Getter
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Autowired
    private static JwtUtil jwtutil;

    @Autowired
    private final JwtProperties jwtProperties;
    public JwtExceptionFilter(JwtUtil jwtutil, JwtProperties jwtProperties) {
        this.jwtutil = jwtutil;
        this.jwtProperties = jwtProperties;
    }

    public static JwtExceptionFilter of(JwtUtil jwtUtil, JwtProperties jwtProperties){
        return new JwtExceptionFilter(jwtutil, jwtProperties);
    }

    /**
     * Jwtfilter보다 먼저 실행하고, Jwtfilter에서 예외 발생하면 여기서 처리
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (UtilException e) {
            setErrorResponse(response, e);
        }
    }

    /**
     * 예외를 종류별로 처리하는 메서드
     */
    public void setErrorResponse(HttpServletResponse response, UtilException utilException) throws IOException {
        response.setStatus(utilException.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        if(utilException.getErrorMsg().equals("REFRESH") || utilException.getErrorMsg().equals("INVALID")){
            response.getWriter().println("{ \"stateCode\" : " + utilException.getErrorCode()
                    + ", \"success\" : \"" + "false"
                    + "\", \"message\" : \"" + utilException.getErrorMsg()
                    + "\", \"data\" : \"" + null
                    + "\" }");
        }
        else {
            String refreshToken = utilException.getErrorMsg();
            String email = jwtutil.getRefreshUserEmail(refreshToken);
            String role = jwtutil.getRole(refreshToken);
            Long id = jwtutil.getId(refreshToken);
            String accessToken = jwtutil.createAccessToken(email, jwtProperties.getSecretKey(), role, id);
            response.getWriter().println("{ \"stateCode\" : " + utilException.getErrorCode()
                    + ", \"success\" : \"" + "false"
                    + "\", \"message\" : \"" + "새로운 AccessToken입니다."
                    + "\", \"data\" : \"" + accessToken
                    + "\" }");
        }
    }

}
