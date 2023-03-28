package com.project.finalproject.application.entity.enums;

public enum ApplicationStatus {

    APPLY("서류지원"), INTERVIEW("실무면접"), PASS("최종합격"),FAIL("불합격");

    private String status;

    ApplicationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
