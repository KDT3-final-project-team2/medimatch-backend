package com.project.finalproject.global.jwt;

public class JwtUtilException extends RuntimeException{

    private JwtUtilExceptionType jwtUtilExceptionType;

    public JwtUtilException(JwtUtilExceptionType jwtUtilExceptionType){
        this.jwtUtilExceptionType = jwtUtilExceptionType;
    }

    public JwtUtilExceptionType getExceptionType() {
        return jwtUtilExceptionType;
    }

}
