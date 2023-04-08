package com.project.finalproject.applicant.dto.request;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDTO {

    @NotBlank(message = "email은 필수 입력입니다.")
    @Email
    private String applicantEmail;
    @NotBlank(message = "password는 필수 입력입니다.")
    private String applicantPassword;
    private String applicantName;
    private LocalDate applicantBirthDate;
    @NotNull(message = "성별이 전달되지 않았습니다.")
    private Gender applicantGender;
    private String applicantContact;
    @NotNull(message = "학력이 전달되지 않았습니다.")
    private ApplicantEducation applicantEducation;
    @NotNull(message = "경력이 전달되지 않았습니다.")
    private ApplicantWorkExperience applicantWorkExperience;
    @NotNull(message = "직무가 전달되지 않았습니다.")
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
