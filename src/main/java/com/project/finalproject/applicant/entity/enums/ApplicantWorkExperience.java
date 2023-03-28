package com.project.finalproject.applicant.entity.enums;

// 경력
public enum ApplicantWorkExperience {
    NEWCOMER("신입"),
    OEN_YEAR("1년차"),
    FIVE_YEAR_OVER("5년차 이상");

    private String workExperience;

    ApplicantWorkExperience(String workExperience){
        this.workExperience = workExperience;
    }

    public String getWorkExperience(){
        return this.workExperience;
    }
}
