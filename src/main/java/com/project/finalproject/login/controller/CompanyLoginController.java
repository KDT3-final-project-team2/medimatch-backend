package com.project.finalproject.login.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.login.dto.LoginReqDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.login.service.CompanyLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyLoginController {

    private final CompanyLoginService companyLoginService;

    @PostMapping("/company/login")
    public ResponseDTO companyLogin(@RequestBody LoginReqDTO req){
        if(req.getEmail() == null || req.getPassword() == null){
            return new ResponseDTO(401, false, "INVALID","이메일이나 비밀번호가 틀렸습니다.");
        }
        return companyLoginService.companyLogin(req);
    }

    @PostMapping("/admin/login")
    public ResponseDTO adminLogin(@RequestBody LoginReqDTO req){
        if(req.getEmail() == null || req.getPassword() == null){
            return new ResponseDTO(401, false, "INVALID","이메일이나 비밀번호가 틀렸습니다.");
        }
        return companyLoginService.adminLogin(req);
    }
}
