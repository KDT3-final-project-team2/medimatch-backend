package com.project.finalproject.company.entity.enums;

public enum TermsType {
    SERVICE("서비스이용약관"), PRIVACY("개인정보처리방침"), THIRD_PARTY("제3자정보제공"), MARKETING("개인정보마케팅이용");

    private String types;

    TermsType(String types) {
        this.types = types;
    }

    public String getTypes() {
        return types;
    }
}
