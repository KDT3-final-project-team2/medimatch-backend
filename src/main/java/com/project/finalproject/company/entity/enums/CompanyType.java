package com.project.finalproject.company.entity.enums;

public enum CompanyType {
    COMPANY("기업회원"), ADMIN("관리자");

    private String companyType;

    CompanyType(String companyType){
        this.companyType = companyType;
    }

    public String getCompanyType(){
        return this.companyType;
    }
}
