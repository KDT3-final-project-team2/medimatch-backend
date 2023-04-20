package com.project.finalproject.searchpost.dto;

import com.project.finalproject.jobpost.entity.Jobpost;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResDTO {

    private Long jobpostId;
    private String jobpostTitle;
    private String jobpostSector;
    private String jobpostEducation;
    private String jobpostWorkExperience;
    private Integer jobpostRecruitNum;
    private String jobpostDueDate;
    private String jobpostStatus;
    private String jobpostFilePath;
    private String companyName;

    private String companyAddress;

    public PostResDTO(Jobpost jobpost){

        this.jobpostId = jobpost.getId();

        this.jobpostTitle = jobpost.getTitle();

        switch (jobpost.getSector()){
            case DOCTOR: this.jobpostSector = "의사";
            break;
            case NURSE: this.jobpostSector = "간호사";
            break;
            case NURSE_AIDE: this.jobpostSector = "간호조무사";
            break;
            case MEDICAL_RECORDS_PROFESSIONAL: this.jobpostSector = "원무과";
            break;
            case MEDICAL_TECHNICIAN: this.jobpostSector = "의료기사";
            break;
        }

        switch (jobpost.getEducation()){
            case HIGH_SCHOOL: this.jobpostEducation = "고졸";
            break;
            case JUNIOR_COLLEGE: this.jobpostEducation = "초대졸";
            break;
            case UNIVERSITY: this.jobpostEducation = "대졸";
            break;
            case MASTER_AND_DOCTOR: this.jobpostEducation = "석박사";
            break;
        }

        switch (jobpost.getExperience()){
            case NEWCOMER: this.jobpostWorkExperience = "신입";
            break;
            case ONE_YEAR: this.jobpostWorkExperience = "1년차";
            break;
            case TWO_YEAR: this.jobpostWorkExperience = "2년차";
            break;
            case THREE_YEAR: this.jobpostWorkExperience = "3년차";
            break;
            case FOUR_YEAR: this.jobpostWorkExperience = "4년차";
            break;
            case FIVE_YEAR_OVER: this.jobpostWorkExperience = "5년차 이상";
            break;
        }

        this.jobpostRecruitNum = jobpost.getRecruitNum();

        this.jobpostDueDate = String.valueOf(jobpost.getDueDate());

        switch (jobpost.getStatus()){
            case OPEN: this.jobpostStatus = "모집중";
            break;
            case CLOSED: this.jobpostStatus = "마감";
            break;
            case DISCARD: this.jobpostStatus = "폐기";
            break;
        }

        this.jobpostFilePath = jobpost.getFilepath();

        this.companyName = jobpost.getCompany().getName();

        this.companyAddress = jobpost.getCompany().getAddress();

    }
}
