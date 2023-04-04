package com.project.finalproject.term.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.entity.enums.TermType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermDetailResponseDTO {

    /*
    "Terms" : List{
termId,
termContent,
termType,
termVersion,
termCreateDate,
termEditDate,
termStatus,
companyId
}

     */
    private Long termId;
    private String termContent;
    private String termType;
    private String termVersion;
    private LocalDateTime termCreateDate;
    private LocalDateTime termEditDate;
    private String termStatus;
    private Long companyId;


    // 약관 전체목록 조회
    public TermDetailResponseDTO(Term term) {
        this.termId = term.getId();
        this.termContent = term.getContent();
        this.termType = term.getType().getType();
        this.termVersion = term.getVersion();
        this.termCreateDate = term.getCreateDate();
        this.termEditDate = term.getEditDate();
        this.termStatus = term.getStatus().getStatus();
        this.companyId = term.getCompany().getId();
    }

}
