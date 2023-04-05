package com.project.finalproject.logout.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.logout.dto.LogoutReqDTO;
import com.project.finalproject.logout.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LogoutController {
    private final LogoutService logoutService;

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseDTO logout(HttpServletRequest request){

        return logoutService.logout(LogoutReqDTO.builder()
                        .authorizationHeader(request.getHeader(HttpHeaders.AUTHORIZATION))
                        .refreshHeader(request.getHeader("REFRESH"))
                        .build());
    }
}
