package com.project.finalproject.global.exception.base;

public abstract class CustomException extends RuntimeException{
    public abstract CustomExceptionType getExceptionType();
}
