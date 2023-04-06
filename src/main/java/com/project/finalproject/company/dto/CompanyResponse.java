package com.project.finalproject.company.dto;

import com.project.finalproject.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기업 회원 DTO
 */
public class CompanyResponse {

    /**
     * 기업 회원 데이터 출력 DTO
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InfoDTO{
        Long companyId; // 기업 아이디
        String email; // 기업 이메일
        String companyNm; // 기업 이름
        String contact; // 기업 연락처
        String regNum; // 사업자등록번호
        String companyAddr; // 기업 주소
        String ceoName; //대표자 이름
        String url; //회사 홈페이지 주소

    }
}
