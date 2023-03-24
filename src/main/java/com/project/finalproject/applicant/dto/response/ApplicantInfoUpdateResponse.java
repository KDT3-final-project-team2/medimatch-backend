package com.project.finalproject.applicant.dto.response;

import com.project.finalproject.applicant.entity.Applicant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantInfoUpdateResponse {
    private String name;
    private String birthDate;
    private String contact;
    private String education;
    private Integer workExperience;
    private String sector;

    @Builder
    public ApplicantInfoUpdateResponse(Applicant applicant) {
        this.name = applicant.getName();
        this.birthDate = applicant.getBirthDate();
        this.contact = applicant.getContact();
        this.education = applicant.getEducation();
        this.workExperience = applicant.getWorkExperience();
        this.sector = applicant.getSector();
    }
}
