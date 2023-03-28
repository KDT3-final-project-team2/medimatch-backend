package com.project.finalproject.jobpost.entity.enums;

public enum JobpostWorkExperience {
    NEWCOMER("신입"),
    OEN_YEAR("1년차"),
    TWO_YEAR("2년차"),
    THREE_YEAR("3년차"),
    FOUR_YEAR("4년차"),
    FIVE_YEAR_OVER("5년차 이상");

    String workExperience;

    JobpostWorkExperience(String workExperience){
        this.workExperience = workExperience;
    }

    public String JobpostWorkExperience(){
        return this.workExperience;
    }
}
