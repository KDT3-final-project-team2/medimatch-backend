package com.project.finalproject.logout.dto;

import lombok.*;
import org.springframework.web.HttpRequestHandler;

@Getter
@Setter
@Builder
public class LogoutReqDTO {
    private String authorizationHeader;
    private String refreshHeader;
}
