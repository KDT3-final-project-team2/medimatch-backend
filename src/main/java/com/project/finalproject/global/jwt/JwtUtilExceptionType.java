package com.project.finalproject.global.jwt;

import com.project.finalproject.global.exception.base.CustomExceptionType;
import org.springframework.http.HttpStatus;

public enum JwtUtilExceptionType {
    USER_ACCESS_TOKEN_UN_AUTHORIZED(HttpStatus.FORBIDDEN, -405,"개인 회원 토큰 인증 불가, 다시 로그인 해주세요"),
    COMPANY_ACCESS_TOKEN_UN_AUTHORIZED(HttpStatus.FORBIDDEN, -406, "기업 회원 토큰 인증 불가, 다시 로그인 해주세요."),
    ADMIN_ACCESS_TOKEN_UN_AUTHORIZED( HttpStatus.FORBIDDEN , -407,"관리자 토큰 인증 불가, 다시 로그인 해주세요."),
    ACCESS_TOKEN_EXPIRATION_DATE(HttpStatus.FORBIDDEN, -408, "토큰 유효기간 만료, /refresh 로 재발급 받으세요"),
    REFRESH_TOKEN_EXPIRATION_DATE(HttpStatus.FORBIDDEN, -409, "refresh 토큰 유효기간 만료, 다시 로그인 해주세요."),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, -410, "유효하지 않은 토큰입니다. 다시 로그인 해주세요.");

    private HttpStatus httpStatus;
    private int errorCode;
    private String errorMsg;

    JwtUtilExceptionType(HttpStatus httpStatus, int errorCode, String errorMsg){
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return this.errorMsg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
