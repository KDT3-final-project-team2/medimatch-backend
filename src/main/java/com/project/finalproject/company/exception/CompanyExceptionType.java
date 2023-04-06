package com.project.finalproject.company.exception;

import com.project.finalproject.global.exception.base.CustomExceptionType;

public enum CompanyExceptionType implements CustomExceptionType {
    NOT_FOUND_USER(-201, "존재하지 않는 사용자 입니다."),
    IO_EXCEPTION(-202, "IOException error")
    ;

    private int errorCode;
    private String errorMsg;

    CompanyExceptionType(int errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return this.getMessage();
    }

    @Override
    public int getErrorCode() {
        return this.getErrorCode();
    }
}
