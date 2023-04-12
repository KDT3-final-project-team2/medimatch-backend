package com.project.finalproject.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.global.dto.ErrorDTO;
import com.project.finalproject.global.jwt.utils.JwtProperties;
import com.project.finalproject.global.jwt.utils.JwtUtil;
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
        } catch (JwtUtilException ex) {
            setErrorResponse(response, ex);
        }
    }

    /**
     * 예외를 종류별로 처리하는 메서드
     */
    public void setErrorResponse(HttpServletResponse response, JwtUtilException utilException) throws IOException {
        response.setStatus(utilException.getExceptionType().getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper om = new ObjectMapper();

        ErrorDTO errorDTO = ErrorDTO.builder()
                .errorCode(utilException.getExceptionType().getErrorCode())
                .errorMessage(utilException.getExceptionType().getMessage())
                .build();

        //(DTO -> json)
        String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(errorDTO);
        response.getWriter().write(jsonStr);

    }

}
