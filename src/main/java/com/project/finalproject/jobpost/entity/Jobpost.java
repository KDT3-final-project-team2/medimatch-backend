package com.project.finalproject.jobpost.entity;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "jobpost")
public class Jobpost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobpost_serial_number")
    private Long id; //PK

    @Column(name = "jobpost_title")
    private String title; //공고 제목

    @Column(name = "jobpost_create_date")
    private LocalDate startDate; //공고 시작 날짜

    @Column(name = "jobpost_changed_date")
    private LocalDate moddate;  // 수정 날짜

    @Column(name = "jobpost_due_date")
    private LocalDate finishDate; //마감 날짜

    @Column(name = "jobpost_views")
    private Integer views; //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_serial_number")
    private Company company;

}
