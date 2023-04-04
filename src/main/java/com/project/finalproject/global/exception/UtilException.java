package com.project.finalproject.global.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilException extends RuntimeException {

    private Integer errorCode;// = 0;
    private HttpStatus httpStatus;// = HttpStatus.FORBIDDEN;
    private String errorMsg;// = null;
}
