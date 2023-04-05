package com.project.finalproject.company.dto;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.application.entity.Application;
import com.project.finalproject.jobpost.entity.Jobpost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 기업 회원 지원자 출력 DTO
 */
public class CompanyApplicationResponse {

    /**
     * 지원자 정보 출력
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApplicantInfoDTO{
        Long applicantId; //지원자 id
        String name; // 지원자 이름
        String email; // 지원자 이메일
        LocalDate birth; // 지원자 생일
        String gender; // 지원자 성별
        String contact; // 지원자 연락처
        String filePath; // 지원자 이력서 파일 경로
        String education; //지원자 학력
        String workExperience; //지원자 경력
        String sector; // 지원자 직무
        Long jobpostId; //채용공고 id
        String jobpostStatus; //채용공고 상태
        String jobpostTitle; //채용공고 제목
        LocalDateTime jobpostDueDate; //채용공고 마감일
        String memo; //메모
        String applicationStatus; //지원자 진행현황
        LocalDateTime applyDate; //지원서날짜
        LocalDateTime interviewDate; //면접날짜

        @Builder
        public ApplicantInfoDTO(Application application) {
            Applicant applicant = application.getApplicant();
            Jobpost jobpost = application.getJobpost();

            this.applicantId = applicant.getId();
            this.name = applicant.getName();
            this.email = applicant.getEmail();
            this.birth = applicant.getBirthDate();
            this.gender = applicant.getGender().getGender();
            this.contact = applicant.getContact();
            this.education = applicant.getEducation().getEducation();
            this.workExperience = applicant.getWorkExperience().getWorkExperience();
            this.sector = applicant.getSector().getSector();

            this.jobpostId = jobpost.getId();
            this.jobpostStatus = jobpost.getStatus().getStatus();
            this.jobpostTitle = jobpost.getTitle();
            this.jobpostDueDate = jobpost.getDueDate();

            this.filePath = application.getFilepath();
            this.memo = application.getMemo();
            this.applicationStatus = application.getStatus().getStatus();
            this.applyDate = application.getApplyDate();
            this.interviewDate = application.getInterviewDate();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ApplicationsForCompanyResponseDTO {


        private HashMap<String, Integer> applicantAgeCount;
        private HashMap<String, Integer> applicantGenderCount;
        private HashMap<String, Integer> applicantEducationCount;
        private HashMap<String, Integer> jobpostTitleCount;


    }
}
