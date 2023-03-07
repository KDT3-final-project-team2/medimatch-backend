package com.project.finalproject.global.exception;

import com.project.finalproject.global.exception.base.CustomExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalExceptionType implements CustomExceptionType {
    OK(0, HttpStatus.OK, "OK"),
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "Bad request"),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "Not found"),
    INTERNAL_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    JWT_NOT_ALLOWED(405, HttpStatus.METHOD_NOT_ALLOWED, "Not Allowed");


    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String errorMsg;

    GlobalExceptionType(Integer errorCode, HttpStatus httpStatus, String errorMsg){
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }
}
