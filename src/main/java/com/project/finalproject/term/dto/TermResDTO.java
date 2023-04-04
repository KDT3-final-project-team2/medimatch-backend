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
public class TermResDTO {
    private Long id;
    private String content;

    private TermType type;
    private String version;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    private TermStatus status;

    // 약관 전체목록 조회
    @Getter
    public static class TermList {
        private Long termId;    // 약관id
        private String type;  // 약관종류
        private String version; // 버전
        private LocalDateTime creatDate;    // 등록일
        private LocalDateTime editDate; // 수정일
        private String status;  // 약관상태

        @Builder
        public TermList(Term term) {
            this.termId = term.getId();
            this.type = term.getType().getType();
            this.version = term.getVersion();
            this.creatDate = term.getCreateDate();
            this.editDate = term.getEditDate();
            this.status = term.getStatus().getStatus();
        }
    }

    // 약관 상세조회
    @Getter
    public static class TermDetail {
        private Long termId;    // 약관id
        private String content; // 내용
        private String type;  // 약관종류
        private String version; // 버전
        private LocalDateTime createDate;   //생성일
        private LocalDateTime editDate; // 수정일
        private String status;  // 약관상태

        @Builder
        public TermDetail(Term term) {
            this.termId = term.getId();
            this.content = term.getContent();
            this.type = term.getType().getType();
            this.version = term.getVersion();
            this.createDate = term.getCreateDate();
            this.editDate = term.getEditDate();
            this.status = term.getStatus().getStatus();
        }
    }

}
