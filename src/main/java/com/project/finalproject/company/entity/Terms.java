package com.project.finalproject.company.entity;

import com.project.finalproject.company.entity.enums.TermsType;

import javax.persistence.*;
import java.time.LocalDate;

//기업 약관
@Entity
@Table(name = "terms")
public class Terms {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_serial_number")
    private Long id; //PK

    @Column(name = "terms_type")
    private TermsType type; //약관 타입?

    @Column(name = "terms_version")
    private String version; //약관 버전

    @Column(name = "terms_create_date")
    private LocalDate createDate; //약관 생성 날짜

    @Column(name = "terms_edit_date")
    private LocalDate editDate; //약관 수정 날짜

    @Column(name = "terms_title")
    private String title; //약관 제목

    @Column(name = "terms_contents", columnDefinition = "TEXT")
    private String contents; //약관 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_serial_number")
    private Company company; //약관을 작성한 기업

}
