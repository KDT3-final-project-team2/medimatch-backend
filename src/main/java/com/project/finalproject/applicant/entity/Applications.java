package com.project.finalproject.applicant.entity;

import com.project.finalproject.applicant.entity.enums.ProgressStatus;
import com.project.finalproject.jobpost.entity.Jobpost;

import javax.persistence.*;
import java.time.LocalDate;

//지원서
@Entity
@Table(name = "applications")
public class Applications {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_serial_number")
    private Long id; //PK

    @Column(name = "application_status_type")
    private ProgressStatus progressStatus; //진행 상태 (UNREAD, 이력서 미열람, 서류 합격, 면접 대기중 ,면접합격 ,불합격, 최종 합격)

    @Column(name = "application_interview_date")
    private LocalDate interviewDate; //면접 확정일

    @Column(name = "application_file_path")
    private String filePath; //지원 이력서 파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobpost_serial_number")
    private Jobpost jobpost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_serial_number")
    private Applicant applicant;
}
