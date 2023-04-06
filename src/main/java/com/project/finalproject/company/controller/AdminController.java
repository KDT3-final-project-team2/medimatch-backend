package com.project.finalproject.company.controller;

import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.company.service.AdminService;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/companies")
    public ResponseDTO<?> showCompanyList(@AuthenticationPrincipal LoginResDTO userDetails) {
        String email = userDetails.getEmail();

        List<AdminCompanyRes.CompanyListDTO> companyList = adminService.showCompanyList(CompanyType.COMPANY);

        return new ResponseDTO<>().ok(companyList, "companyList success");
    }
}
