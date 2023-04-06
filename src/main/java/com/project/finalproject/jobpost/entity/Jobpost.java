package com.project.finalproject.jobpost.entity;

import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.company.dto.CompanyJobpostRequest;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.jobpost.entity.enums.JobpostEducation;
import com.project.finalproject.jobpost.entity.enums.JobpostStatus;
import com.project.finalproject.jobpost.entity.enums.JobpostWorkExperience;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 공고
 */
@Entity
@Table(name = "tb_jobpost")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jobpost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobpost_id")
    private Long id; //채용공고 ID

    @Column(name = "jobpost_title")
    private String title; //채용 공고 제목

    @Column(name = "jobpost_education")
    @Enumerated(EnumType.STRING) //"고졸, 초대졸, 대졸, 석박사"
    private JobpostEducation education; //채용공고 최소학력 요구사항

    @Column(name = "jobpost_work_experience")
    @Enumerated(EnumType.STRING)
    private JobpostWorkExperience experience; //채용공고 최소경력 요구사항

    @Column(name = "jobpost_start_date")
    private LocalDateTime startDate; //채용공고 시작일

    @Column(name = "jobpost_due_date")
    private LocalDateTime dueDate; //채용공고 마감일

    @CreatedDate
    @Column(name = "jobpost_create_date",updatable = false)
    private LocalDateTime createDate; //채용공고 생성일

    @LastModifiedDate
    @Column(name = "jobpost_edit_date")
    private LocalDateTime editDate; //채용공고 수정일

    @Column(name = "jobpost_status")
    @Enumerated(EnumType.STRING)
    private JobpostStatus status; //채용공고 상태

    @Column(name = "jobpost_recruit_num")
    private Integer recruitNum; //채용공고 모집인원

    @Column(name= "jobpost_filepath")
    private String filepath; //채용공고 파일경로

    @Column(name= "jobpost_sector")
    @Enumerated(EnumType.STRING)
    private Sector sector; //채용공고 직무

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company; //채용공고를 작성한 기업회원

    public Jobpost createJobpost(CompanyJobpostRequest.CreateDTO createDTO, Company company){
        return Jobpost.builder()
                .title(createDTO.getTitle())
                .education(createDTO.getEducation())
                .experience(createDTO.getWorkExperience())
                .startDate(createDTO.getStartDate())
                .dueDate(createDTO.getDueDate())
                .status(JobpostStatus.OPEN)
                .recruitNum(createDTO.getRecruitNum())
                .filepath(createDTO.getFilePath())
                .sector(createDTO.getSector())
                .company(company)
                .build();
    }

    public void changeStatus(){
        this.status = JobpostStatus.DISCARD;
    }

    public Jobpost updateJobpost(CompanyJobpostRequest.UpdateDTO updateDTO, Company company){
        if(!updateDTO.getTitle().isEmpty()) this.title = updateDTO.getTitle();
        if(updateDTO.getStartDate() != null) this.startDate = updateDTO.getStartDate();
        if(updateDTO.getDueDate() != null) this.dueDate = updateDTO.getDueDate();
        if(updateDTO.getSector() != null) this.sector = updateDTO.getSector();
        if(updateDTO.getEducation() != null) this.education = updateDTO.getEducation();
        if(updateDTO.getWorkExperience() != null) this.experience = updateDTO.getWorkExperience();
        if(updateDTO.getRecruitNum() != null) this.recruitNum = updateDTO.getRecruitNum();
        if(updateDTO.getJobpostStatus() != null) this.status = updateDTO.getJobpostStatus();

        return Jobpost.builder()
                .title(title)
                .startDate(startDate)
                .dueDate(dueDate)
                .sector(sector)
                .education(education)
                .experience(experience)
                .recruitNum(recruitNum)
                .filepath(filepath)
                .status(status)
                .company(company)
                .build();
    }

}
