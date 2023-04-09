package com.project.finalproject.global.exception;

import com.project.finalproject.company.exception.CompanyException;
import com.project.finalproject.global.dto.ErrorDTO;
import com.project.finalproject.global.exception.base.CustomException;
import com.project.finalproject.jobpost.exception.JobpostException;
import com.project.finalproject.term.exception.TermException;
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

    //company exception handler
    @ExceptionHandler(value = CompanyException.class)
    public ErrorDTO handleCompanyException(CompanyException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())
                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }

    //jobpost exception handler
    @ExceptionHandler(value = JobpostException.class)
    public ErrorDTO handleJobpostException(JobpostException je){
        return ErrorDTO.builder()
                .errorCode(je.getExceptionType().getErrorCode())
                .errorMessage(je.getExceptionType().getMessage())
                .build();
    }

    //term exception handler
    @ExceptionHandler(value = TermException.class)
    public ErrorDTO handleTermException(TermException te){
        return ErrorDTO.builder()
                .errorCode(te.getExceptionType().getErrorCode())
                .errorMessage(te.getExceptionType().getMessage())
                .build();
    }

}

