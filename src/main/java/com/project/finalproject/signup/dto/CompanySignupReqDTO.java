package com.project.finalproject.signup.dto;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import lombok.*;

import static com.project.finalproject.company.entity.enums.CompanyType.COMPANY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySignupReqDTO {

    private String email; //회사이메일(겸 아이디)
    private String password; //비밀번호
    private String companyName; //회사명
    private String address; //회사주소
    private String contact; //연락처
    private String regNum; //사업자번호
    private String representativeName; //대표자이름
    private String url; //홈페이지 주소


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
                .companyType(COMPANY)
                .build();
    }
}