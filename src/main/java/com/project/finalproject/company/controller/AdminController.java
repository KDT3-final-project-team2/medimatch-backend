package com.project.finalproject.company.controller;

import com.project.finalproject.company.dto.AdminApplicantRes;
import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.company.service.AdminService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
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
    public ResponseDTO<?> showCompanyList() {
        //TODO : 토큰 완성되면 기업회원목록조회 파라미터 수정하기

        List<AdminCompanyRes.CompanyListDTO> companyList = adminService.showCompanyList(CompanyType.COMPANY);

        return new ResponseDTO<>().ok(companyList, "companyList success");
    }

    @GetMapping("/applicants")
    public ResponseDTO<?> showApplicantList() {
        //TODO : 토큰 완성되면 개인회원목록조회 파라미터 수정하기
        List<AdminApplicantRes.ApplicantListDTO> applicantList = adminService.showApplicantList();

        return new ResponseDTO<>().ok(applicantList, "applicantList success");
    }

}
