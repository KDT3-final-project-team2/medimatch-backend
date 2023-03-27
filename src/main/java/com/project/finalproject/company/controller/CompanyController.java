package com.project.finalproject.company.controller;


import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/info")
    public ResponseDTO getCompanyInfo() {
        Long companyId = 1L; //Todo
        return new ResponseDTO<>(200, true, companyService.getCompanyInfo(companyId), "Information of current Company");
    }
}
