package com.project.finalproject.global.exception;

import com.project.finalproject.global.exception.base.CustomException;
import com.project.finalproject.global.exception.base.CustomExceptionType;

public class GlobalException extends CustomException {

    private GlobalExceptionType globalExceptionType;

    public GlobalException(GlobalExceptionType globalExceptionType){
        this.globalExceptionType = globalExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.globalExceptionType;
    }
}
