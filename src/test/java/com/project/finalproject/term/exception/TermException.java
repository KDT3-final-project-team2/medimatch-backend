package com.project.finalproject.term.exception;

import com.project.finalproject.global.exception.base.CustomException;
import com.project.finalproject.global.exception.base.CustomExceptionType;

public class TermException extends CustomException {

    private TermExceptionType termExceptionType;

    public TermException(TermExceptionType termExceptionType){
        this.termExceptionType = termExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.termExceptionType;
    }
}
