package com.project.finalproject.login.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.login.dto.LoginReqDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.login.service.ApplicantLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplicantLoginController {

    private final ApplicantLoginService applicantLoginService;

    @PostMapping("/applicant/login")
    public ResponseDTO userLogin(@RequestBody LoginReqDTO req){

        if(req.getEmail() == null || req.getPassword() == null){
            return new ResponseDTO(401, false,"INVALID", "이메일이나 비밀번호가 틀렸습니다.");
        }
        return applicantLoginService.userLogin(req);
    }
}
