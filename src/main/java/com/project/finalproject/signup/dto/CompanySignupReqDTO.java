package com.project.finalproject.signup.dto;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySignupReqDTO {

    private String companyName;
    private String representativeName;
    private String regNum;
    private String email;
    private String password;
    private String contact;
    private String address;
    private String url;
    private CompanyType companyType;
    //필요시사용
    public Company toEntity(){
        return Company.builder()
                .name(companyName)
                .representativeName(representativeName)
                .regNum(regNum)
                .email(email)
                .password(password)
                .contact(contact)
                .address(address)
                .url(url)
                .companyType(companyType)
                .build();
    }
}
