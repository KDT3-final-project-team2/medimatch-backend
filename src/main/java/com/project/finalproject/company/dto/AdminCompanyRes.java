package com.project.finalproject.company.dto;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import lombok.Builder;
import lombok.Getter;

public class AdminCompanyRes {

    @Getter
    public static class CompanyListDTO {
        private Long companyId; //회사id

        private String companyName; //회사명

        private String representativeName; //대표자 이름

        private String regNum; //사업자번호

        private String email; //회사 이메일

        private String contact; //회사 연락처

        private String address; //회사주소

        private String companyType;

        @Builder
        public CompanyListDTO(Company company) {
            this.companyId = company.getId();
            this.companyName = company.getName();
            this.representativeName = company.getRepresentativeName();
            this.regNum = company.getRegNum();
            this.email = company.getEmail();
            this.contact = company.getContact();
            this.address = company.getAddress();

            if (company.getCompanyType() == CompanyType.COMPANY) {
                this.companyType = company.getCompanyType().getCompanyType();
            }

        }
    }
}
