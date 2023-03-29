package com.project.finalproject.applicant.dto.request;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Sector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoUpdateRequestDTO {
  /*
    "applicantPassword", string
"applicantName", string
"applicantContact", string
"applicantEducation", string
"applicantWorkExperience", long
"applicantSector" string
*/
    private String applicantPassword;
    private String applicantName;
    private String applicantContact;
    private ApplicantEducation applicantEducation;
    private ApplicantWorkExperience applicantWorkExperience;
    private Sector applicantSector;

    public Applicant toEntity(){
        return Applicant.builder()
                .password(this.applicantPassword)
                .name(this.applicantName)
                .contact(this.applicantContact)
                .education(this.applicantEducation)
                .workExperience(this.applicantWorkExperience)
                .sector(this.applicantSector)
                .build();
    }
}
