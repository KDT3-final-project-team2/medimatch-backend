package com.project.finalproject.term.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TermTypeList {
    //SERVICE("서비스이용약관"), PRIVACY("개인정보처리방침"), THIRD_PARTY("제3자정보제공"), MARKETING("개인정보마케팅이용");
    private TermDetailResponseDTO service;
    private TermDetailResponseDTO privacy;
    private TermDetailResponseDTO thirdParty;
    private TermDetailResponseDTO marketing;

}
