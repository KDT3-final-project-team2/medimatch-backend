package com.project.finalproject.global.exception;

import com.project.finalproject.company.exception.CompanyException;
import com.project.finalproject.global.dto.ErrorDTO;
import com.project.finalproject.global.exception.base.CustomException;
import com.project.finalproject.jobpost.exception.JobpostException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler implements ErrorController {

    @ExceptionHandler(value = GlobalException.class)
    public ErrorDTO handleGlobalException(CustomException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())
                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }

    //Custom exception handler
    @ExceptionHandler(value = {CompanyException.class, JobpostException.class})
    public ErrorDTO handleCustomException(CustomException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())
                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }
}

