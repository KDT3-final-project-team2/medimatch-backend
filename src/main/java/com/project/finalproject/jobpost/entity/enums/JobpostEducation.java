package com.project.finalproject.jobpost.entity.enums;

public enum JobpostEducation {
    HIGH_SCHOOL("고졸"),
    COMMUNITY_COLLEGE("초대졸"),
    UNIVERSITY("대졸"),
    GRAD_SCHOOL("석박사");

    String education;

    JobpostEducation(String education){
        this.education = education;
    }

    public String getEducation(){
        return this.education;
    }
}
