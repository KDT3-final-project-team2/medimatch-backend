package com.project.finalproject.applicant.entity;

import com.project.finalproject.applicant.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//지원자
@Entity
@Table(name = "applicant")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_serial_number")
    private Long id; //pk

    @Column(name = "applicant_email")
    private String email; //지원자 이메일

    @Column(name = "applicant_password")
    private String password; //비밀번호

    @Column(name = "applicant_name")
    private String name; //이름

    @Column(name = "applicant_birthdate")
    private String birthDate; //생년월일

    @Column(name = "applicant_gender")
    private Gender gender; //성별

    @Column(name = "applicant_contact")
    private String contact; //연락처

    @Column(name = "applicant_file_path")
    private String applicationFilePath; //지원서파일경로

    @Column(name = "applicant_education")
    private String education; //학력

    @Column(name = "applicant_work_experience")
    private Integer workExperience; // X년차.

    @Column(name = "applicant_sector")
    private String sector; //지원분야

}
