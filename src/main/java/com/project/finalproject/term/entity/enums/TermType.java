package com.project.finalproject.term.entity.enums;

public enum TermType {

    SERVICE("서비스이용약관"), PRIVACY("개인정보처리방침"), THIRD_PARTY("제3자정보제공"), MARKETING("개인정보마케팅이용");

    private String type;

    TermType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
