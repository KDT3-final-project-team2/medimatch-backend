package com.project.finalproject.company.service;

import com.project.finalproject.company.dto.AdminApplicantRes;
import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.enums.CompanyType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AdminService {

    // 기업회원 목록조회
    List<AdminCompanyRes.CompanyListDTO> showCompanyList(CompanyType companyType);

    //개인회원 목록조회
    List<AdminApplicantRes.ApplicantListDTO> showApplicantList();

    //기업회원 total 인원
    long getTotalCompanyCount(CompanyType companyType);
    //기업회원 당해연도 가입인원
    long getYearlyCompanyCount(CompanyType companyType);
    //기업회원 당월 가입인원
    long getMonthlyCompanyCount(CompanyType companyType);
    //기업회원 당일 가입인원
    long getDailyCompanyCount(CompanyType companyType);
    //기업회원 월별 가입인원
    long getMonthlyCompanyCountByJoinDateMonth(CompanyType companyType,int month);
    //개인회원 total 인원
    long getTotalApplicantCount();
    //개인회원 당해연도 인원
    long getYearlyApplicantCount();
    //개인회원 당월 가입인원
    long getMonthlyApplicantCount();
    //개인회원 당일 가입인원
    long getDailyApplicantCount();
    //개인회원 월별 가입인원
    long getMonthlyApplicantCountByJoinDateMonth(int month);
    //개인회원 직무별 인원
    Map<String, Long> getSectorCount();
}
