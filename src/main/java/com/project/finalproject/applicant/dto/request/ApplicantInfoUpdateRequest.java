package com.project.finalproject.applicant.dto.request;


import com.project.finalproject.applicant.entity.Applicant;
import lombok.Data;

@Data
public class ApplicantInfoUpdateRequest {
    private String name;
    private String birthDate;
    private String contact;
    private String education;
    private Integer workExperience;
    private String sector;

    public Applicant toEntity() {
        return Applicant.builder()
                .name(name)
                .birthDate(birthDate)
                .contact(contact)
                .education(education)
                .workExperience(workExperience)
                .sector(sector)
                .build();
    }
}
