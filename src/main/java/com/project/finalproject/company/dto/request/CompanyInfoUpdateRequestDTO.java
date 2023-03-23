package com.project.finalproject.company.dto.request;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyInfoUpdateRequestDTO {
    private String companyName;
    private String companyEmail;
    private String companyAddress;
    private String companyContact;
    private String companyPassword;
    private String companyRegistrationNumber;
    private String companyRepresentativeName;
    private String companyUrl;

    public Company toEntity(){
        return Company.builder()
                .name(companyName)
                .email(companyEmail)
                .address(companyAddress)
                .contact(companyContact)
                .password(companyPassword)
                .regNum(Long.parseLong(companyRegistrationNumber))
                .representativeName(companyRepresentativeName)
                .url(companyUrl)
                .build();
    }
}

/*
Request
Body:
"companyName", string
"companyEmail", string
"companyAddress", string
"companyContact", string
"companyPassword", string
"companyRegistrationNumber", string
"companyRepresentativeName", string
"companyUrl" string

Response
Header:
Body:
"success"
 */