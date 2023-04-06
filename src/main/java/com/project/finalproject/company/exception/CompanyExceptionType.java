package com.project.finalproject.company.exception;

import com.project.finalproject.global.exception.base.CustomExceptionType;

public enum CompanyExceptionType implements CustomExceptionType {
    NOT_FOUND_USER(-201, "존재하지 않는 사용자 입니다."),
    IO_EXCEPTION(-202, "IOException error"),
    NOT_FOUND_APPLICATION(-202, "존재하지 않는 지원서 입니다."),
    DATA_UPDATE_ERROR(-203, "데이터 업데이트중에 에러 발생, 담당자에게 문의하세요.")
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
