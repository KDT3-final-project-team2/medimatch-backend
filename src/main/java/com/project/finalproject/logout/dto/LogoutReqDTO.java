package com.project.finalproject.logout.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class LogoutReqDTO {
    private String authorizationHeader;
    private String refreshHeader;
}
