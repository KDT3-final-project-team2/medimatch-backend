package com.project.finalproject.signup.controller;

import com.project.finalproject.signup.dto.CompanySignupReqDTO;
import com.project.finalproject.signup.service.CompanySignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final CompanySignupService companySignupService;

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
