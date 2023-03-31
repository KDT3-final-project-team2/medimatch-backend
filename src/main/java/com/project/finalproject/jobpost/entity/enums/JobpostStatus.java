package com.project.finalproject.jobpost.entity.enums;

public enum JobpostStatus {
    CLOSED("마감"),
    OPEN("모집중"),
    DISCARD("폐기");

    private String status;

    JobpostStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
