package com.project.finalproject.company.controller;

import com.project.finalproject.applicant.service.ApplicantService;
import com.project.finalproject.company.dto.AdminApplicantRes;
import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.company.service.AdminService;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final ApplicantService applicantService;

    /**
     * 관리자 기업회원목록조회
     * @param userDetail 토큰
     * @return 기업회원리스트
     */
    @GetMapping("/companies")
    public ResponseDTO<?> showCompanyList(@AuthenticationPrincipal LoginResDTO userDetail) {
        if(!userDetail.getRole().equals("ADMIN")){
            return new ResponseDTO(401, false,"Unauthorized", "접근권한이 없습니다.");

        } else{

            List<AdminCompanyRes.CompanyListDTO> companyList = adminService.showCompanyList(CompanyType.COMPANY);

            return new ResponseDTO<>().ok(companyList, "companyList success");
        }

    }

    /**
     * 관리자 개인회원목록조회
     * @param userDetail 토큰
     * @return 개인회원리스트
     */
    @GetMapping("/applicants")
    public ResponseDTO<?> showApplicantList(@AuthenticationPrincipal LoginResDTO userDetail) {
        if(!userDetail.getRole().equals("ADMIN")){

            return new ResponseDTO(401, false,"Unauthorized", "접근권한이 없습니다.");

        } else{

            List<AdminApplicantRes.ApplicantListDTO> applicantList = adminService.showApplicantList();

            return new ResponseDTO<>().ok(applicantList, "applicantList success");
        }
    }

    /**
     * 관리자 통계
     * @param userDetail 토큰
     * @return
     * 1.기업회원수(all, 당해, 당월, 당일)
     * 2.개인회원수(all, 당해, 당월, 당일)
     * 3.매년 각 월의 신규가입자 수,
     * 4.지원자 가입자들의 직무별 인원수
     */
    @GetMapping("/statistics")
    public ResponseDTO<Map<String, Long>> getMemberCount(@AuthenticationPrincipal LoginResDTO userDetail) {
        if(!userDetail.getRole().equals("ADMIN")){

            return new ResponseDTO(401, false,"Unauthorized", "접근권한이 없습니다.");

        } else{

            Map<String, Long> memberCountMap = new HashMap<>();
            memberCountMap.put("기업회원Total인원", adminService.getTotalCompanyCount(CompanyType.COMPANY));
            memberCountMap.put("기업회원당해인원", adminService.getYearlyCompanyCount(CompanyType.COMPANY));
            memberCountMap.put("기업회원당월인원", adminService.getMonthlyCompanyCount(CompanyType.COMPANY));
            memberCountMap.put("기업회원당일인원", adminService.getDailyCompanyCount(CompanyType.COMPANY));
            memberCountMap.put("개인회원Total인원", adminService.getTotalApplicantCount());
            memberCountMap.put("개인회원당해인원", adminService.getYearlyApplicantCount());
            memberCountMap.put("개인회원당월인원", adminService.getMonthlyApplicantCount());
            memberCountMap.put("개인회원당일인원", adminService.getDailyApplicantCount());

            // 월별 가입인원
            for (int month = 1; month <= 12; month++) {
                String key = String.format("%d월가입인원", month);
                Long value1 = adminService.getMonthlyCompanyCountByJoinDateMonth(CompanyType.COMPANY, month);
                Long value2 = adminService.getMonthlyApplicantCountByJoinDateMonth(month);
                memberCountMap.put(key, value1+value2);
            }

            //개인회원 직무별 인원수
            memberCountMap.putAll(adminService.getSectorCount());

            return new ResponseDTO<Map<String, Long>>().ok(memberCountMap,"count success");
        }
    }
}
