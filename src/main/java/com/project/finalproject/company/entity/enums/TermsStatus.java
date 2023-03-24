package com.project.finalproject.company.entity.enums;

public enum TermsStatus {

    USE("사용"), TEMPORARY("임시저장"), DISCARD("폐기");

    private String status;

    TermsStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
