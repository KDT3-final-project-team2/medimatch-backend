package com.project.finalproject.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
    private int stateCode;
    private boolean success;
    private T data;

    private String message;

    public ResponseDTO(int stateCode, boolean success, T data, String msg){
        this.stateCode = stateCode;
        this.success = success;
        this.data = data;
        this.message = msg;
    }

    // response 성공시
    public ResponseDTO<T> ok(T data, String msg) {
        return new ResponseDTO<>(200, true, data, msg);
    }

}
