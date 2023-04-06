package com.project.finalproject.jobpost.exception;

import com.project.finalproject.global.exception.base.CustomExceptionType;

public enum JobpostExceptionType implements CustomExceptionType {
    NOT_FOUND_PAGE(-301, "없는 게시글 입니다."),
    NOT_FOUND_FILE(-302, "첨부 파일이 없습니다."),
    POSTS_DISCARD_ALREADY(-303, "이미 폐기된 채용공고 입니다.");


    private int errorCode;
    private String errorMsg;

    JobpostExceptionType(int errorCode, String errorMsg){
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
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
