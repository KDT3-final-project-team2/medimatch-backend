package com.project.finalproject.company.dto;

import com.project.finalproject.company.entity.Terms;
import com.project.finalproject.company.entity.enums.TermsStatus;
import com.project.finalproject.company.entity.enums.TermsType;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TermsDTO {

    private Long id; //PK

    @NotNull
    private TermsType type; //약관 타입

    @NotEmpty
    private String version; //약관 버전

    private LocalDateTime createDate, editDate; //약관 생성일, 수정일

    @NotEmpty
    private String title; //약관 제목

    @NotEmpty
    private String contents; //약관 내용

    @NotEmpty
    private String name;// 작성자(기업명)

    @NotNull
    private TermsStatus termsStatus;    //약관 상태

    public TermsDTO(Terms terms) {
        this.id = terms.getId();
        this.type = TermsType.valueOf(terms.getType().getTypes());
        this.version = terms.getVersion();
        this.createDate = terms.getCreateDate();
        this.editDate = terms.getEditDate();
        this.title = terms.getTitle();
        this.contents = terms.getContents();
        this.name = terms.getCompany().getName();
        this.termsStatus = TermsStatus.valueOf(terms.getTermsStatus().getStatus());
    }
}
