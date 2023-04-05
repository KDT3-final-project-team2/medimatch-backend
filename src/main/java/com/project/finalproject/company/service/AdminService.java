package com.project.finalproject.company.service;

import com.project.finalproject.company.dto.AdminApplicantRes;
import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.enums.CompanyType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {

    // 기업회원 목록조회
    List<AdminCompanyRes.CompanyListDTO> showCompanyList(CompanyType companyType);

    //개인회원 목록조회
    List<AdminApplicantRes.ApplicantListDTO> showApplicantList();

}
