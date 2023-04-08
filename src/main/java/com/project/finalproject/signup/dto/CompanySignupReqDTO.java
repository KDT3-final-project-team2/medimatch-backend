package com.project.finalproject.signup.dto;

import com.project.finalproject.company.entity.Company;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

import static com.project.finalproject.company.entity.enums.CompanyType.COMPANY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySignupReqDTO {

    @NotBlank(message = "email은 필수 입력입니다.")
    @Email
    private String companyEmail; //회사이메일(겸 아이디)
    @NotBlank(message = "password는 필수 입력입니다.")
    private String companyPassword; //비밀번호
    private String companyName; //회사명
    private String companyAddress; //회사주소
    private String companyContact; //연락처
    private String companyRegNum; //사업자번호
    private String companyRepresentative; //대표자이름
    private String companyUrl; //홈페이지 주소


    //필요시사용
    public Company toEntity(){
        return Company.builder()
                .name(companyName)
                .representativeName(companyRepresentative)
                .regNum(companyRegNum)
                .email(companyEmail)
                .password(companyPassword)
                .contact(companyContact)
                .address(companyAddress)
                .url(companyUrl)
                .companyType(COMPANY)
                .signupDate(LocalDate.now())
                .build();
    }
}