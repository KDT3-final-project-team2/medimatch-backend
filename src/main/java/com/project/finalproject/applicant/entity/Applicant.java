package com.project.finalproject.applicant.entity;

import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 지원자 (개인회원)
 */
@Getter
@Builder
@Table(name = "tb_applicant")
@Entity
public class Applicant {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicant_id")
    Long id; //pk

    @Column(name = "applicant_email")
    String email; //지원자 email

    @Column(name = "applicant_password")
    String password; //지원자 비밀번호

    @Column(name = "applicant_name")
    String name; //지원자 이름

    @Column(name = "applicant_birth_date")
    LocalDate birthDate; //지원자 생년월일

    @Column(name = "applicant_gender")
    @Enumerated(EnumType.STRING)
    Gender gender; //지원자 성별

    @Column(name = "applicant_contact")
    String contact; //지원자 연락처

    @Column(name = "applicant_education")
    ApplicantEducation education; //지원자 학력

    @Column(name = "applicant_work_experience")
    ApplicantWorkExperience workExperience; //지원자 경력

    @Column(name = "applicant_sector")
    @Enumerated(EnumType.STRING)
    Sector sector; //지원직무

    @Column(name = "applicant_file_path")
    String filePath; //이력서 파일 경로

    @CreatedDate
    @Column(name = "applicant_signup_date")
    LocalDate signUpDate; //가입 날짜

    @Column(name = "applicant_disable_date")
    LocalDate disableDate; //휴면 전환 날짜

}
