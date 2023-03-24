package com.project.finalproject.company.entity;

import com.project.finalproject.company.entity.enums.CompanyType;
import lombok.*;

import javax.persistence.*;

//기업 회원
@Entity
@Table(name = "tb_company")

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_serial_number")
    private Long id; //PK

    @Column(name = "company_registration_number")
    private Long regNum; //사업자 등록 번호

    @Column(name = "company_email")
    private String email; // 이메일

    @Column(name = "compnay_password")
    private String password; //비밀번호

    @Column(name = "compnay_name")
    private String name; //기업명

    @Column(name = "company_contact")
    private String contact; //기업 연락처

    @Column(name = "company_representative_name")
    private String representativeName; //대표자 이름

    @Column(name = "company_address")
    private String address; //회사 주소

    @Column(name = "company_url")
    private String url; //회사 홈페이지

    @Column(name = "company_type")
    private CompanyType companyType; // 기업 회원 타입 (기업/어드민)


}
