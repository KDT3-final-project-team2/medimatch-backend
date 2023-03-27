package com.project.finalproject.login.controller;

import com.project.finalproject.login.dto.LoginReqDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.login.service.ApplicantLoginService;
import com.project.finalproject.login.service.CompanyLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final ApplicantLoginService applicantLoginService;
    private final CompanyLoginService companyLoginService;

    /**
     * 개인회원 로그인
     */
    @PostMapping("/applicant/login")
    public LoginResDTO applicantLogin(@RequestBody LoginReqDTO reqDTO) throws Exception {
        if(reqDTO.getEmail() == null || reqDTO.getPassword() == null){
            return LoginResDTO.builder()
                    .role("INVALID")
                    .build();
        }

        return applicantLoginService.applicantLogin(reqDTO);
    }
    @PostMapping("/test")
    public String test(){
        return "TEST";
    }


    /**
     * 기업회원 로그인
     */
    @PostMapping("/company/login")
    public LoginResDTO companyLogin(@RequestBody LoginReqDTO reqDTO) throws Exception {
        if(reqDTO.getEmail() == null || reqDTO.getPassword() == null){
            return LoginResDTO.builder()
                    .role("INVALID")
                    .build();
        }

        return companyLoginService.companyLogin(reqDTO);
    }
}
