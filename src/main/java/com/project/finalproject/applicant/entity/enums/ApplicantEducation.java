package com.project.finalproject.applicant.entity.enums;

//최종 학력
public enum ApplicantEducation {
    HIGH_SCHOOL("고졸"),
    JUNIOR_COLLEGE("초대졸"),
    UNIVERSITY("대졸"),
    MASTER_AND_DOCTOR("석박사");

    private String education;

    ApplicantEducation(String education){
        this.education = education;
    }

    public String getEducation(){
        return this.education;
    }
}
