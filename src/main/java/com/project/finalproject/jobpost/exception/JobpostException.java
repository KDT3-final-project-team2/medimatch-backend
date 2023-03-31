package com.project.finalproject.jobpost.exception;

import com.project.finalproject.global.exception.base.CustomException;
import com.project.finalproject.global.exception.base.CustomExceptionType;

public class JobpostException extends CustomException {

    private JobpostExceptionType jobpostExceptionType;

    public JobpostException(JobpostExceptionType jobpostExceptionType){
        this.jobpostExceptionType = jobpostExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.jobpostExceptionType;
    }
}
