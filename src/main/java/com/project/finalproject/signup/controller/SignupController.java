package com.project.finalproject.signup.controller;

import com.project.finalproject.signup.dto.ApplicantSignupReqDTO;
import com.project.finalproject.signup.dto.CompanySignupReqDTO;
import com.project.finalproject.signup.service.ApplicantSignupService;
import com.project.finalproject.signup.service.CompanySignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final ApplicantSignupService applicantSignupService;
    private final CompanySignupService companySignupService;
    /**
     * 개인회원 회원가입
     */
    @PostMapping("/applicant/signup")
    public String signUp(@RequestBody ApplicantSignupReqDTO applicantSignupReqDTO){
        if(applicantSignupReqDTO.getEmail() == null || applicantSignupReqDTO.getPassword() == null){
            return "아이디와 비밀번호를 정확히 입력해주세요";
        }

        return applicantSignupService.signUp(applicantSignupReqDTO);
    }

    /**
     * 기업회원 회원가입
     */
    @PostMapping("/company/signup")
    public String signUp(@RequestBody CompanySignupReqDTO companySignupReqDTO){
        if(companySignupReqDTO.getEmail() == null || companySignupReqDTO.getPassword() == null){
            return "아이디와 비밀번호를 정확히 입력해주세요";
        }

        return companySignupService.signUp(companySignupReqDTO);
    }
}
