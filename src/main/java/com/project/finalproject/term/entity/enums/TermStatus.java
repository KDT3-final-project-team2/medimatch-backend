package com.project.finalproject.term.entity.enums;

public enum TermStatus {

    USE("사용"), DISCARD("폐기");

    private String status;

    TermStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
