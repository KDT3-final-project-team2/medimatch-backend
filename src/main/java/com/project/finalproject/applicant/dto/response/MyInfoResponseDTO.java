package com.project.finalproject.applicant.dto.response;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyInfoResponseDTO {

    public MyInfoResponseDTO(Applicant applicant){
        this.applicantId = applicant.getId();
        this.applicantEmail = applicant.getEmail();
        this.applicantName = applicant.getName();
        this.applicantBirthDate = applicant.getBirthDate();
        this.applicantGender = applicant.getGender();
        this.applicantContact = applicant.getContact();
        this.applicantEducation = applicant.getEducation();
        this.applicantWorkExperience = applicant.getWorkExperience();
        this.applicantSector = applicant.getSector();
        this.applicant_file_path=applicant.getFilePath();
    }

    private Long applicantId;
    private String applicantEmail;
    private String applicantName;
    private LocalDate applicantBirthDate;
    private Gender applicantGender;
    private String applicantContact;
    private ApplicantEducation applicantEducation;
    private ApplicantWorkExperience applicantWorkExperience;
    private Sector applicantSector;
    private String applicant_file_path;

}
