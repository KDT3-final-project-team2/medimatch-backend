package com.project.finalproject.company.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyInfoUpdateController {

    public String updateCompanyInfo(){
        //CompanyInfoUpdateService.updateCompanyInfo();
        return "success";
    }
}
