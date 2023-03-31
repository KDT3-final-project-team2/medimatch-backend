package com.project.finalproject.company.exception;

import com.project.finalproject.global.exception.base.CustomException;
import com.project.finalproject.global.exception.base.CustomExceptionType;

public class CompanyException extends CustomException {

    private CompanyExceptionType companyExceptionType;

    public CompanyException(CompanyExceptionType companyExceptionType){
        this.companyExceptionType = companyExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.companyExceptionType;
    }
}
