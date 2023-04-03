package com.project.finalproject.applicant.dto.response;

import com.project.finalproject.application.entity.Application;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.jobpost.entity.Jobpost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppliedJobpostResponseDTO {

    private Long applicationId;
    private String companyName;
    private String jobpostTitle;
    private Long jobpostId;
    private String applicationStatusType;
    private LocalDateTime applicationInterviewTime;
    private LocalDateTime applicationApplyDate;
    private String applicationFilepath;

    public AppliedJobpostResponseDTO(Company company, Jobpost jobpost, Application application) {
        this.applicationId = application.getId();
        this.companyName = company.getName();
        this.jobpostTitle = jobpost.getTitle();
        this.jobpostId = jobpost.getId();
        this.applicationStatusType = application.getStatus().getStatus();
        this.applicationInterviewTime = application.getInterviewDate();
        this.applicationApplyDate = application.getApplyDate();
        this.applicationFilepath = application.getFilepath();
    }
}
