package com.project.finalproject.company.dto;

import com.project.finalproject.applicant.entity.Applicant;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class AdminApplicantRes {
    @Getter
    public static class ApplicantListDTO {
        private Long applicantId; //지원자 id
        private String email; //지원자 email
        private String name; //지원자 이름
        private LocalDate birthDate; //지원자 생년월일
        private String gender;  //지원자 성별
        private String contact; //지원자 연락처
        private String education;   //지원자 학력
        private String workExperience; //지원자 경력
        private String sector;  //지원직무

    @Builder
        public ApplicantListDTO(Applicant applicant) {
        this.applicantId = applicant.getId();
        this.email = applicant.getEmail();
        this.name = applicant.getName();
        this.birthDate = applicant.getBirthDate();
        this.gender = applicant.getGender().getGender();
        this.contact = applicant.getContact();
        this.education = applicant.getEducation().getEducation();
        this.workExperience = applicant.getWorkExperience().getWorkExperience();
        this.sector = applicant.getSector().getSector();
        }
    }
}
