package com.project.finalproject.applicant.entity.enums;

//직무
public enum Sector {
    DOCTOR("의사"),
    NURSE("간호사"),
    NURSE_AIDE("간호조무사"),
    MEDICAL_RECORDS_PROFESSIONAL("원무과"),
    MEDICAL_TECHNICIAN("의료기사");

    private String sector;

    Sector(String sector) {
        this.sector = sector;
    }

    public String getSector() {
        return sector;
    }
}
