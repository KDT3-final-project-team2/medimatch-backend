package com.project.finalproject.company.entity;

import com.project.finalproject.company.dto.CompanyRequest;
import com.project.finalproject.company.entity.enums.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 병원 (기업회원)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "tb_company")
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id; // PK

    @Column(name = "company_email")
    private String email; //회사 이메일

    @Column(name = "company_password")
    private String password; //비밀번호

    @Column(name = "company_name")
    private String name; //회사명

    @Column(name = "company_address")
    private String address; //회사주소

    @Column(name = "company_contact")
    private String contact; //연락처

    @Column(name = "company_reg_num")
    private String regNum; //사업자번호

    @Column(name = "company_representative_name")
    private String representativeName; //대표자 이름

    @Column(name = "company_homepage")
    private String url; //홈페이지 주소

    @Column(name = "company_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType; //COMPANY:기업회원 ADMIN:관리자

    @CreatedDate
    @Column(name = "company_signup_date", updatable = false)
    private LocalDate signupDate; //가입날짜

    @Column(name = "company_disable_date")
    private LocalDate disableDate; //탈퇴날짜

    public void companyResign(String email, LocalDate disableDate) {
        this.email = email;
        this.disableDate = disableDate;
    }

    public Company updateData(CompanyRequest.UpdateInfoDTO updateInfoDTO){
        if(updateInfoDTO.getCompanyNm() != null) this.name = updateInfoDTO.getCompanyNm();
        if(updateInfoDTO.getContact() != null) this.contact = updateInfoDTO.getContact();
        if(updateInfoDTO.getRegNum() != null) this.regNum = updateInfoDTO.getRegNum();
        if(updateInfoDTO.getCompanyAddr() != null) this.address = updateInfoDTO.getCompanyAddr();
        if(updateInfoDTO.getCeoName() != null) this.representativeName = updateInfoDTO.getCeoName();
        if(updateInfoDTO.getUrl() != null) this.url = updateInfoDTO.getUrl();

        return Company.builder()
                .id(id)
                .name(name)
                .email(email)
                .address(address)
                .password(password)
                .contact(contact)
                .regNum(regNum)
                .representativeName(representativeName)
                .url(url)
                .companyType(companyType)
                .signupDate(signupDate)
                .disableDate(disableDate)
                .build();
    }

    public void changeCompanyPassword(String password){
        this.password = password;
    }
}
