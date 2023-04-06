package com.project.finalproject.applicant.entity;

import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 지원자 (개인회원)
 */
@Getter
@Setter
@Builder
@Table(name = "tb_applicant")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicant_id")
    private Long id; //pk

    @Column(name = "applicant_email")
    private String email; //지원자 email

    @Column(name = "applicant_password")
    private String password; //지원자 비밀번호

    @Column(name = "applicant_name")
    private String name; //지원자 이름

    @Column(name = "applicant_birth_date")
    private LocalDate birthDate; //지원자 생년월일

    @Column(name = "applicant_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender; //지원자 성별

    @Column(name = "applicant_contact")
    private String contact; //지원자 연락처

    @Column(name = "applicant_education")
    @Enumerated(EnumType.STRING)
    private ApplicantEducation education; //지원자 학력

    @Column(name = "applicant_work_experience")
    @Enumerated(EnumType.STRING)
    private ApplicantWorkExperience workExperience; //지원자 경력

    @Column(name = "applicant_sector")
    @Enumerated(EnumType.STRING)
    private Sector sector; //지원직무

    @Column(name = "applicant_file_path")
    private String filePath; //이력서 파일 경로

    @CreatedDate
    @Column(name = "applicant_signup_date")
    private LocalDate signUpDate; //가입 날짜

    @Column(name = "applicant_disable_date")
    private LocalDate disableDate; //휴면 전환 날짜
    public void applicantResign(String email, LocalDate disableDate) {
        this.email = email;
        this.disableDate = disableDate;
    }
}
