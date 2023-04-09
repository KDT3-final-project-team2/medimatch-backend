package com.project.finalproject.application.entity;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.application.entity.enums.ApplicationStatus;
import com.project.finalproject.company.dto.CompanyApplicationRequest;
import com.project.finalproject.jobpost.entity.Jobpost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 지원서 entity
 * 개인 사용자 (지원자) 와 채용 공고를 이어주는 mapping table
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id; //PK

    @Column(name = "application_status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;   //진행현황

    @Column(name = "application_interview_date")
    private LocalDateTime interviewDate;    //인터뷰날짜

    @Column(name = "application_filepath")
    private String filepath;    //이력서 경로

    @Column(name = "application_memo")
    private String memo;    //메모 (기업회원)

    @Column(name = "application_apply_date")
    private LocalDateTime applyDate; //지원 날짜

    @Column(name = "application_pass_date")
    private LocalDateTime passDate; //합격 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobpost_id")
    private Jobpost jobpost;


    @Builder
    public Application(Long id, ApplicationStatus status, LocalDateTime interviewDate,
                       String filepath, String memo, LocalDateTime applyDate,
                       LocalDateTime passDate, Applicant applicant, Jobpost jobpost) {
        this.id = id;
        this.status = status;
        this.interviewDate = interviewDate;
        this.filepath = filepath;
        this.memo = memo;
        this.applyDate = applyDate;
        this.passDate = passDate;
        this.applicant = applicant;
        this.jobpost = jobpost;
    }

    public Application (Applicant applicant, Jobpost jobpost, String jobpostResumeDirectory) {
        this.status = ApplicationStatus.APPLY;
        //this.interviewDate = null;
        this.filepath = jobpostResumeDirectory;
        //this.memo = null;
        this.applyDate = LocalDateTime.now();
        //this.passDate = null;
        this.applicant = applicant;
        this.jobpost = jobpost;
    }

    //지원서 상태변경
    public Application updateStatus(CompanyApplicationRequest.StatusReqDTO statusReqDTO){
        if(statusReqDTO.getStatus() != null) this.status = statusReqDTO.getStatus();
        if(statusReqDTO.getInterviewDate() != null) this.interviewDate = statusReqDTO.getInterviewDate();
        if(statusReqDTO.getPassDate() != null) this.passDate = statusReqDTO.getPassDate();
        if(statusReqDTO.getMemo() != null && !statusReqDTO.getMemo().equals("")) this.memo = statusReqDTO.getMemo();

        return Application.builder()
                .id(id)
                .status(status)
                .interviewDate(interviewDate)
                .filepath(filepath)
                .memo(memo)
                .applyDate(applyDate)
                .applicant(applicant)
                .passDate(passDate)
                .applicant(applicant)
                .jobpost(jobpost)
                .build();
    }


}
