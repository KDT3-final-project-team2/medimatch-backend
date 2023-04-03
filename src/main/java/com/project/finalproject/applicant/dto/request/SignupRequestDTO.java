package com.project.finalproject.applicant.dto.request;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import lombok.*;

import javax.persistence.Access;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDTO {

    private String applicantEmail;
    private String applicantPassword;
    private String applicantName;
    private LocalDate applicantBirthDate;
    private Gender applicantGender;
    private String applicantContact;
    private ApplicantEducation applicantEducation;
    private ApplicantWorkExperience applicantWorkExperience;
    private Sector applicantSector;

    public Applicant toEntity(){
        return Applicant.builder()
                .email(this.applicantEmail)
                .password(this.applicantPassword)
                .name(this.applicantName)
                .birthDate(this.applicantBirthDate)
                .gender(this.applicantGender)
                .contact(this.applicantContact)
                .education(this.applicantEducation)
                .workExperience(this.applicantWorkExperience)
                .sector(this.applicantSector)
                .signUpDate(LocalDate.now())
                .build();
    }
}
