package com.project.finalproject.company.entity;

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

    @Column(name = "company_url")
    private LocalDate disableDate; //탈퇴날짜


}
