package com.project.finalproject.applicant.entity.enums;

//성별
public enum Gender {
    M("남"),
    W("여");

    private String gender;

    Gender(String gender){
        this.gender = gender;
    }

    public String getGender(){
        return this.gender;
    }
}
