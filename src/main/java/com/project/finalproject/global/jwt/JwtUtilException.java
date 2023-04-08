package com.project.finalproject.global.jwt;

import com.project.finalproject.global.exception.base.CustomException;
import com.project.finalproject.global.exception.base.CustomExceptionType;

public class JwtUtilException extends RuntimeException{

    private JwtUtilExceptionType jwtUtilExceptionType;

    public JwtUtilException(JwtUtilExceptionType jwtUtilExceptionType){
        this.jwtUtilExceptionType = jwtUtilExceptionType;
    }

    public JwtUtilExceptionType getExceptionType() {
        return jwtUtilExceptionType;
    }
}
