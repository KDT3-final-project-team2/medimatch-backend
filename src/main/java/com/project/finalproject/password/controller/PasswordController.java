package com.project.finalproject.password.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.password.dto.PasswordDTO;
import com.project.finalproject.password.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;
    private final JwtUtil jwtUtil;

    /**
     * 로그인한 상태에서 비밀번호 변경
     */
    @PostMapping("/change")
    public ResponseDTO changePassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtUtil.extractToken(authorizationHeader);
        String role = jwtUtil.getRole(token);
        Long id = jwtUtil.getId(token);
        passwordDTO.setRole(role);
        passwordDTO.setId(id);

        return passwordService.changePassword(passwordDTO);
    }

    /**
     * 로그인하지 않은 상태에서 비밀번호 초기화
     */
    @PostMapping("/reset")
    public ResponseDTO resetPassword(@RequestBody PasswordDTO passwordDTO) throws MessagingException {
        return passwordService.resetPassword(passwordDTO);
    }
}
