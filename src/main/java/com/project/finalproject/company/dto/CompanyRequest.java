package com.project.finalproject.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기업회원 입력받을 DTO
 */
public class CompanyRequest {

    /**
     * 정보 수정시 입력받을 DTO
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateInfoDTO{
        String companyNm; // 기업 이름
        String contact; // 기업 연락처
        String regNum; // 사업자등록번호
        String companyAddr; // 기업 주소
        String ceoName; //대표자 이름
        String url; //회사 홈페이지 주소
    }
}
