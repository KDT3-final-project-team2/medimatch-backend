package com.project.finalproject.signup.dto;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantSignupReqDTO {

    private String email;
    private String password;
    private String name;
    private String birthDate;
    private Gender gender;
    private String contact;
    private String education;
    private Integer workExperience;
    private String sector;

    //필요시 사용
    public Applicant toEntity() {
        return Applicant.builder()
                .email(email)
                .password(password)
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .contact(contact)
                .education(education)
                .workExperience(workExperience)
                .sector(sector)
                .build();
    }
}