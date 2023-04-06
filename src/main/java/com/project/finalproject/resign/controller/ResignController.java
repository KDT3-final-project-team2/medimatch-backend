package com.project.finalproject.resign.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.resign.dto.ResignDTO;
import com.project.finalproject.resign.service.ResignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ResignController {

    private final ResignService resignService;
    private final JwtUtil jwtUtil;

    /**
     * 회원탈퇴(탈퇴 날짜를 저장하고, 이메일만 지운다.)
     */
    @PostMapping("/deactive")
    public ResponseDTO resign(HttpServletRequest request){
        String token = jwtUtil.extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Long id = jwtUtil.getId(token);
        String role = jwtUtil.getRole(token);

        return resignService.resign(ResignDTO.builder()
                        .id(id)
                        .role(role)
                        .build());
    }
}
